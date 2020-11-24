package tech.nilu.web3j.repository.erc20.templates

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.web3j.abi.EventEncoder
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger

class ERC20BasicImpl : Contract, ERC20Basic {

    constructor(
        contractAddress: String,
        web3j: Web3j,
        credentials: Credentials,
        gasProvider: ContractGasProvider
    ) : super(BINARY, contractAddress, web3j, credentials, gasProvider)

    constructor(
        contractAddress: String,
        web3j: Web3j,
        transactionManager: TransactionManager,
        gasProvider: ContractGasProvider
    ) : super(BINARY, contractAddress, web3j, transactionManager, gasProvider)

    override fun name(): RemoteCall<String> {
        val function = Function(
            FUNC_NAME,
            listOf(),
            listOf<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    override fun totalSupply(): RemoteCall<BigInteger> {
        val function = Function(
            FUNC_TOTAL_SUPPLY,
            listOf(),
            listOf<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    override fun rate(): RemoteCall<BigInteger> {
        val function = Function(
            FUNC_RATE,
            listOf(),
            listOf<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    override fun decimals(): RemoteCall<BigInteger> {
        val function = Function(
            FUNC_DECIMALS,
            listOf(),
            listOf<TypeReference<*>>(object : TypeReference<Uint8?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    override fun symbol(): RemoteCall<String> {
        val function = Function(
            FUNC_SYMBOL,
            listOf(),
            listOf<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    override fun isPayable(): RemoteCall<Boolean> {
        val function = Function(
            FUNC_IS_PAYABLE,
            listOf(),
            listOf<TypeReference<*>>(object : TypeReference<Bool?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, Boolean::class.java)
    }

    override fun balanceOf(me: String): RemoteCall<BigInteger> {
        val function = Function(
            FUNC_BALANCE_OF,
            listOf(Address(me)),
            listOf<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    override fun transfer(to: String, value: BigInteger): RemoteCall<TransactionReceipt> {
        val function = Function(
            FUNC_SEND_TRANSACTION,
            listOf(Address(to), Uint256(value)),
            emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    override fun getTransferEvents(transactionReceipt: TransactionReceipt): List<TransferEvent.Response> {
        val valueList = extractEventParametersWithLog(EVENT_TRANSFER, transactionReceipt)
        return valueList.map {
            TransferEvent.Response(
                log = it.log,
                from = it.indexedValues[0].value as String,
                to = it.indexedValues[1].value as String,
                value = it.nonIndexedValues[0].value as BigInteger
            )
        }
    }

    override fun transferEventObservable(startBlock: DefaultBlockParameter, endBlock: DefaultBlockParameter): Flow<TransferEvent.Response> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress()).apply {
            addSingleTopic(EventEncoder.encode(EVENT_TRANSFER))
        }
        return web3j.ethLogFlowable(filter).map {
            val eventValues = extractEventParametersWithLog(EVENT_TRANSFER, it)
            TransferEvent.Response(
                log = it,
                from = eventValues.indexedValues[0].value as String,
                to = eventValues.indexedValues[1].value as String,
                value = eventValues.nonIndexedValues[0].value as BigInteger
            )
        }.asFlow()
    }

    override fun createTokens(weiValue: BigInteger): RemoteCall<TransactionReceipt> {
        val function = Function(
            FUNC_CREATE_TOKENS,
            listOf(),
            emptyList()
        )
        return executeRemoteCallTransaction(function, weiValue)
    }

    override fun prepareTransferData(to: String, value: BigInteger): String {
        val function = Function(
            FUNC_TRANSFER,
            listOf(Address(to), Uint256(value)),
            emptyList()
        )
        return FunctionEncoder.encode(function)
    }

    companion object {

        const val BINARY = ""
        const val FUNC_NAME = "name"
        const val FUNC_TOTAL_SUPPLY = "totalSupply"
        const val FUNC_RATE = "rate"
        const val FUNC_DECIMALS = "decimals"
        const val FUNC_SYMBOL = "symbol"
        const val FUNC_BALANCE_OF = "balanceOf"
        const val FUNC_SEND_TRANSACTION = "sendTransaction"
        const val FUNC_IS_PAYABLE = "isPayable"
        const val FUNC_CREATE_TOKENS = "createTokens"
        const val FUNC_TRANSFER = "transfer"

        val EVENT_TRANSFER = Event(
            "Transfer",
            listOf<TypeReference<*>>(
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Uint256?>() {}
            )
        )

        fun load(
            contractAddress: String,
            web3j: Web3j,
            credentials: Credentials,
            gasProvider: ContractGasProvider
        ): ERC20BasicImpl = ERC20BasicImpl(contractAddress, web3j, credentials, gasProvider)

        fun load(
            contractAddress: String,
            web3j: Web3j,
            transactionManager: TransactionManager,
            gasProvider: ContractGasProvider
        ): ERC20BasicImpl = ERC20BasicImpl(contractAddress, web3j, transactionManager, gasProvider)
    }
}
