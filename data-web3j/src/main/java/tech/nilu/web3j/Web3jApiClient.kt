package tech.nilu.web3j

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.*
import org.web3j.ens.EnsResolver
import org.web3j.ens.NameHash
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import tech.nilu.data.dao.NetworkDao
import tech.nilu.data.entity.ContractInfo
import tech.nilu.web3j.entity.DomainInfo
import tech.nilu.web3j.entity.EstimateValues
import tech.nilu.web3j.entity.TransactionDetails
import tech.nilu.web3j.repository.erc20.ERC20Basic
import tech.nilu.web3j.repository.erc20.ERC20BasicImpl
import tech.nilu.web3j.repository.smartcontracts.templates.*
import tech.nilu.web3j.repository.wallet.WalletSdk
import java.math.BigDecimal
import java.math.BigInteger
import java.net.Proxy
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Web3jApiClient @Inject constructor(
    private val networkDao: NetworkDao,
    private val sdk: WalletSdk
) {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .proxy(Proxy.NO_PROXY)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    proxy(null)
                }
            }
            .build()
    }

    private suspend fun getWeb3j(): Web3j? =
        Web3j.build(HttpService(networkDao.getActiveNetwork()?.address, okHttpClient, false))

    suspend fun getTransactionDetails(hash: String): TransactionDetails {
        val web3j = checkNotNull(getWeb3j())
        return coroutineScope {
            val transaction = async { web3j.ethGetTransactionByHash(hash).send() }
            val receipt = async { web3j.ethGetTransactionReceipt(hash).send() }
            TransactionDetails(transaction.await().result, receipt.await().result)
        }
    }

    fun getTransactionReceipt(hash: String): Flow<TransactionReceipt> = flow {
        val web3j = checkNotNull(getWeb3j())
        val processor = PollingTransactionReceiptProcessor(
            web3j,
            TransactionManager.DEFAULT_POLLING_FREQUENCY,
            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH
        )
        emit(processor.waitForTransactionReceipt(hash))
    }

    suspend fun getBalance(address: String): BigInteger? {
        val web3j = checkNotNull(getWeb3j())
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
    }

    suspend fun getBalances(addresses: List<String>): List<BigInteger> {
        val web3j = checkNotNull(getWeb3j())
        return coroutineScope {
            addresses.map { async { web3j.ethGetBalance(it, DefaultBlockParameterName.LATEST).send() } }
                .awaitAll()
                .map { it.balance }
        }
    }

    suspend fun getTotalBalance(addresses: List<String>): BigInteger {
        val web3j = checkNotNull(getWeb3j())
        return coroutineScope {
            addresses.map { async { web3j.ethGetBalance(it, DefaultBlockParameterName.LATEST).send() } }
                .awaitAll()
                .sumOf { it.balance }
        }
    }

    suspend fun getTransferFee(
        contractAddress: String,
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal
    ): EstimateValues {
        val web3j = checkNotNull(getWeb3j())
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            credentials,
            NULL_GAS_PROVIDER
        )
        val data = contract.prepareTransferData(toAddress, Convert.toWei(value, Convert.Unit.ETHER).toBigInteger())
        return getTransferFee(credentials, contractAddress, BigDecimal.ZERO, data)
    }

    suspend fun getTransferFee(
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        data: String
    ): EstimateValues {
        val web3j = checkNotNull(getWeb3j())
        return coroutineScope {
            val nonce = async { web3j.ethGetTransactionCount(credentials.address, DefaultBlockParameterName.LATEST).send() }
            val gasPrice = async { web3j.ethGasPrice().send() }
            val gasValues = EstimateValues(
                nonce = nonce.await().transactionCount,
                gasPrice = gasPrice.await().gasPrice,
                gasLimit = null
            )
            val trx = Transaction(
                credentials.address,
                gasValues.nonce,
                gasValues.gasPrice,
                DEFAULT_GAS_LIMIT,
                toAddress,
                Convert.toWei(value, Convert.Unit.ETHER).toBigInteger(),
                data
            )
            gasValues.copy(gasLimit = web3j.ethEstimateGas(trx).send().amountUsed)
        }
    }

    suspend fun transfer(
        contractAddress: String,
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        estimateValues: EstimateValues
    ): String? {
        val web3j = checkNotNull(getWeb3j())
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            credentials,
            NULL_GAS_PROVIDER
        )
        val data = contract.prepareTransferData(toAddress, Convert.toWei(value, Convert.Unit.ETHER).toBigInteger())
        return transfer(credentials, contractAddress, BigDecimal.ZERO, estimateValues, data)
    }

    suspend fun transfer(
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        estimateValues: EstimateValues,
        data: String
    ): String? {
        val web3j = checkNotNull(getWeb3j())
        val rawTrx = RawTransaction.createTransaction(
            estimateValues.nonce,
            estimateValues.gasPrice,
            estimateValues.gasLimit,
            toAddress,
            Convert.toWei(value, Convert.Unit.ETHER).toBigInteger(),
            data
        )
        val signedMsg = TransactionEncoder.signMessage(rawTrx, credentials)
        return web3j.ethSendRawTransaction(Numeric.toHexString(signedMsg)).send().transactionHash
    }

    suspend fun fetchBasicContract(
        walletId: Long,
        contractAddress: String
    ): ContractInfo {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return coroutineScope {
            val name = async { contract.name().send() }
            val symbol = async { contract.symbol().send() }
            val balance = async { contract.balanceOf(wallet.credentials.address).send() }
            ContractInfo(
                id = 0L,
                walletId = 0L,
                address = contractAddress,
                name = name.await(),
                image = "",
                types = "",
                symbol = symbol.await()
            ).apply { this.balance = balance.await() }
        }
    }

    suspend fun fetchERC20Contract(
        walletId: Long,
        contractAddress: String
    ): ContractInfo {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return coroutineScope {
            val name = async { contract.name().send() }
            val symbol = async { contract.symbol().send() }
            val totalSupply = async { contract.totalSupply().send() }
            val decimals = async { contract.decimals().send() }
            val rate = async { contract.rate().send() }
            val isPayable = async { contract.isPayable().send() }
            ContractInfo(
                id = 0L,
                walletId = 0L,
                address = contractAddress,
                name = "",
                image = "",
                types = "",
                symbol = null
            ).apply {
                tokenName = name.await()
                tokenSymbol = symbol.await()
                tokenTotalSupply = totalSupply.await()
                tokenDecimals = decimals.await()
                tokenRate = rate.await()
                tokenIsPayable = isPayable.await()
            }
        }
    }

    suspend fun fetchERC20Token(
        walletId: Long,
        contractAddress: String
    ): ContractInfo {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        EnsResolver(web3j).resolve(contractAddress)
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return coroutineScope {
            val name = async { contract.name().send() }
            val symbol = async { contract.symbol().send() }
            ContractInfo(
                id = 0L,
                walletId = 0L,
                address = contractAddress,
                name = name.await(),
                image = String.format(ERC20_TOKENS_IMAGE_ADDRESS, contractAddress),
                types = "",
                symbol = symbol.await()
            )
        }
    }

    suspend fun contractBalance(
        walletId: Long,
        contractAddress: String
    ): BigInteger? {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return contract.balanceOf(wallet.credentials.address).send()
    }

    suspend fun contractSymbol(
        walletId: Long,
        contractAddress: String
    ): String? {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val contract: ERC20Basic = ERC20BasicImpl.load(
            contractAddress,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return contract.symbol().send()
    }

    suspend fun contractsSymbol(
        walletId: Long,
        addresses: List<String>
    ): Map<String, String> {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")
        if (addresses.isEmpty()) return emptyMap()

        val web3j = checkNotNull(getWeb3j())
        return coroutineScope {
            addresses
                .map { ERC20BasicImpl.load(it, web3j, wallet.credentials, DEFAULT_GAS_PROVIDER) }
                .map { async { it.contractAddress to it.symbol().send() } }
                .awaitAll()
                .toMap()
        }
    }

    suspend fun getDeploymentFee(
        credentials: Credentials,
        name: String,
        symbol: String,
        decimals: BigInteger,
        totalSupply: BigInteger
    ): EstimateValues {
        val web3j = checkNotNull(getWeb3j())
        val encodedConstructor = FunctionEncoder.encodeConstructor(
            listOf(
                Utf8String(name),
                Utf8String(symbol),
                Uint8(decimals),
                Uint256(totalSupply)
            )
        )
        val data = NiluToken.BINARY + encodedConstructor
        val trx = Transaction(credentials.address, null, null, null, null, null, data)
        return coroutineScope {
            val gasPrice = async { web3j.ethGasPrice().send() }
            val gasLimit = async { web3j.ethEstimateGas(trx).send() }
            EstimateValues(
                nonce = null,
                gasPrice = gasPrice.await().gasPrice,
                gasLimit = gasLimit.await().amountUsed
            )
        }
    }

    suspend fun deployToken(
        credentials: Credentials,
        name: String,
        symbol: String,
        decimals: BigInteger,
        totalSupply: BigInteger,
        estimateValues: EstimateValues
    ): NiluToken? {
        val web3j = checkNotNull(getWeb3j())
        val gasProvider = StaticGasProvider(estimateValues.gasPrice, estimateValues.gasLimit)
        val token = NiluToken.deploy(web3j, credentials, gasProvider, name, symbol, decimals, totalSupply).send()
        return if (token != null && token.contractAddress.isNotEmpty()) {
            token
        } else {
            null
        }
    }

    suspend fun fetchSelectedContracts(walletId: Long): List<ContractInfo> {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val contract = SelectedContracts.load(
            SELECTED_CONTRACTS_ADDRESS,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return coroutineScope {
            val contracts = async { contract.listContracts().send() }
            Json.decodeFromString<Array<ContractInfo>>(contracts.await())
                .map { it.copy(walletId = walletId, address = "0x${it.address}") }
                .toList()
        }
    }

    suspend fun fetchVerifiedContracts(walletId: Long, offset: Long, size: Long): List<ContractInfo> {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val notaryTokens = NotaryTokens.load(
            VERIFIED_CONTRACTS_ADDRESS,
            web3j,
            wallet.credentials,
            DEFAULT_GAS_PROVIDER
        )
        return coroutineScope {
            val tokens = async { notaryTokens.getTokens(BigInteger.valueOf(offset), BigInteger.valueOf(size)).send() }
            Json.decodeFromString<Array<ContractInfo>>(tokens.await())
                .map {
                    it.copy(
                        walletId = walletId,
                        address = "0x${it.address}",
                        image = String.format(NILU_TOKENS_IMAGE_ADDRESS, it.address),
                        types = "ERC20Basic"
                    )
                }
                .toList()
        }
    }

    suspend fun getGasPrice(): BigInteger? {
        val web3j = checkNotNull(getWeb3j())
        return web3j.ethGasPrice().send().gasPrice
    }

    suspend fun resolveDomain(
        walletId: Long,
        domain: String
    ): String? {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val node = NameHash.nameHashAsBytes(domain)
        val registry = NNSRegistry.load(
            NNS_REGISTRY_ADDRESS,
            web3j,
            wallet.credentials,
            NULL_GAS_PROVIDER
        )
        return coroutineScope {
            val resolverAddress = async { registry.resolver(node).send() }
            val resolver = PublicResolver.load(
                resolverAddress.await(),
                web3j,
                wallet.credentials,
                NULL_GAS_PROVIDER
            )
            val address = resolver.addr(node).send()
            if (!WalletUtils.isValidAddress(address)) {
                throw IllegalStateException("Unable to resolve address for name: $domain")
            } else {
                address
            }
        }
    }

    suspend fun reverseResolution(walletId: Long): String {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val node = NameHash.nameHashAsBytes("${wallet.credentials.address.substring(2).toLowerCase(Locale.getDefault())}.addr.reverse")
        val registry = NNSRegistry.load(
            NNS_REGISTRY_ADDRESS,
            web3j,
            wallet.credentials,
            NULL_GAS_PROVIDER
        )
        return coroutineScope {
            val resolver = async { registry.resolver(node).send() }
            val reverseResolver = DefaultReverseResolver.load(
                resolver.await(),
                web3j,
                wallet.credentials,
                NULL_GAS_PROVIDER
            )
            reverseResolver.name(node).send()
        }
    }

    suspend fun queryDomains(
        walletId: Long,
        node: String
    ): DomainInfo {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val registry = NNSRegistry.load(
            NNS_REGISTRY_ADDRESS,
            web3j,
            wallet.credentials,
            NULL_GAS_PROVIDER
        )
        return coroutineScope {
            val owner = async { registry.owner(NameHash.nameHashAsBytes(node)).send() }
            val resolver = async { registry.resolver(NameHash.nameHashAsBytes(node)).send() }
            DomainInfo(owner.await(), resolver.await())
        }
    }

    suspend fun registerDomain(
        walletId: Long,
        subnode: String,
        rootNode: String
    ): Boolean {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val gasPrice = web3j.ethGasPrice().send().gasPrice
        val registrar = SubnodeRegistrar.load(
            SUBNODE_REGISTRAR_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(60000))
        )
        val resolver = PublicResolver.load(
            PUBLIC_RESOLVER_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(55000))
        )
        val registry = NNSRegistry.load(
            NNS_REGISTRY_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(55000))
        )
        val reverseResolver = DefaultReverseResolver.load(
            REVERSE_RESOLVER_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(55000))
        )
        return coroutineScope {
            val owner = async { registry.owner(NameHash.nameHashAsBytes("addr.reverse")).send() }
            val reverseRegistrar = ReverseRegistrar.load(
                owner.await(),
                web3j,
                wallet.credentials,
                StaticGasProvider(gasPrice, BigInteger.valueOf(60000))
            )
            val node = NameHash.nameHashAsBytes("$subnode.$rootNode")
            val reverseNode = NameHash.nameHashAsBytes("${wallet.credentials.address.substring(2).toLowerCase()}.addr.reverse")
            registrar.setSubnodeOwner(
                NameHash.nameHashAsBytes(rootNode),
                Hash.sha3(subnode.toByteArray()),
                wallet.credentials.address.toLowerCase()
            ).send()
            resolver.setAddr(node, wallet.credentials.address.toLowerCase()).send()
            registry.setResolver(node, resolver.contractAddress).send()
            reverseRegistrar.claim(wallet.credentials.address.toLowerCase()).send()
            registry.setResolver(reverseNode, reverseResolver.contractAddress).send()
            reverseResolver.setName(reverseNode, "$subnode.$rootNode").send()
            true
        }
    }

    suspend fun releaseDomain(
        walletId: Long,
        domainToRelease: String
    ): Boolean {
        val wallet = sdk.wallets[walletId] ?: throw IllegalStateException("Wallet not loaded!")

        val web3j = checkNotNull(getWeb3j())
        val gasPrice = web3j.ethGasPrice().send().gasPrice
        val resolver = PublicResolver.load(
            PUBLIC_RESOLVER_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(40000))
        )
        val registry = NNSRegistry.load(
            NNS_REGISTRY_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(35000))
        )
        val reverseResolver = DefaultReverseResolver.load(
            REVERSE_RESOLVER_ADDRESS,
            web3j,
            wallet.credentials,
            StaticGasProvider(gasPrice, BigInteger.valueOf(45000))
        )
        val zeroAddress = Address.DEFAULT.toString()
        val node = NameHash.nameHashAsBytes(domainToRelease)
        val reverseNode = NameHash.nameHashAsBytes("${wallet.credentials.address.substring(2).toLowerCase()}.addr.reverse")
        resolver.setAddr(node, zeroAddress).send()
        registry.setResolver(node, zeroAddress).send()
        registry.setOwner(node, zeroAddress).send()
        reverseResolver.setName(reverseNode, "").send()
        registry.setResolver(reverseNode, zeroAddress).send()
        registry.setOwner(reverseNode, zeroAddress).send()
        return true
    }

    companion object {
        private val DEFAULT_GAS_LIMIT = BigInteger.valueOf(4476768)
        private val NULL_GAS_PROVIDER = StaticGasProvider(null, null)
        private val DEFAULT_GAS_PROVIDER = StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO)

        private const val SELECTED_CONTRACTS_ADDRESS = "0xfd51a808815befb1356eae02549f4ef48884f568"
        private const val VERIFIED_CONTRACTS_ADDRESS = "0xe3Ab83082bB4F1ECDFbA755e5723A368CB1C243a"
        private const val NILU_TOKENS_IMAGE_ADDRESS = "https://raw.githubusercontent.com/NiluPlatform/ERC20-Tokens/master/images/%s.png"
        private const val ERC20_TOKENS_IMAGE_ADDRESS =
            "https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/ethereum/assets/%s/logo.png"

        private const val NNS_REGISTRY_ADDRESS = "0xd878ff289b0033cb4ae35c18f19e901f461f9997"
        private const val PUBLIC_RESOLVER_ADDRESS = "0x821fac8be5c2b44b23616bf0608ecae47e4532cf"
        private const val SUBNODE_REGISTRAR_ADDRESS = "0x1c8a2cb13b22164e74b2b76712b81b4671eb80e0"
        private const val REVERSE_RESOLVER_ADDRESS = "0x25f049ef55c693bc6f0cfb983f329d49479ec43c"
    }
}
