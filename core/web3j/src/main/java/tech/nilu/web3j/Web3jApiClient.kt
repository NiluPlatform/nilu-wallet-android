package tech.nilu.web3j

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.TransactionManager
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import tech.nilu.data.dao.NetworkDao
import tech.nilu.web3j.entity.TransactionDetails
import java.math.BigInteger
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Web3jApiClient @Inject constructor(
    private val networkDao: NetworkDao
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
}
