package tech.nilu.web3j

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
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
import tech.nilu.web3j.entity.EstimateValues
import tech.nilu.web3j.entity.TransactionDetails
import tech.nilu.web3j.repository.erc20.templates.ERC20Basic
import tech.nilu.web3j.repository.erc20.templates.ERC20BasicImpl
import tech.nilu.web3j.repository.erc20.templates.NiluToken
import tech.nilu.web3j.repository.wallet.WalletSdk
import java.math.BigDecimal
import java.math.BigInteger
import java.net.Proxy
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
            StaticGasProvider(null, null)
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
            StaticGasProvider(null, null)
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
            StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO)
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
            StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO)
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
            StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO)
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
            StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO)
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
        val gasProvider = StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO)
        return coroutineScope {
            addresses
                .map { ERC20BasicImpl.load(it, web3j, wallet.credentials, gasProvider) }
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

    companion object {
        private val DEFAULT_GAS_LIMIT = BigInteger.valueOf(4476768)
    }
}
