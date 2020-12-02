package tech.nilu.web3j.repository.erc20.templates

import io.reactivex.Flowable
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigInteger

interface ERC20Basic {

    fun name(): RemoteCall<String>

    fun totalSupply(): RemoteCall<BigInteger>

    fun rate(): RemoteCall<BigInteger>

    fun decimals(): RemoteCall<BigInteger>

    fun symbol(): RemoteCall<String>

    fun isPayable(): RemoteCall<Boolean>

    fun balanceOf(me: String): RemoteCall<BigInteger>

    fun transfer(to: String, value: BigInteger): RemoteCall<TransactionReceipt>

    fun getTransferEvents(transactionReceipt: TransactionReceipt): List<TransferEvent.Response>

    fun transferEventFlowable(startBlock: DefaultBlockParameter, endBlock: DefaultBlockParameter): Flowable<TransferEvent.Response>

    fun createTokens(weiValue: BigInteger): RemoteCall<TransactionReceipt>

    fun prepareTransferData(to: String, value: BigInteger): String
}
