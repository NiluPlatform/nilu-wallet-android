package tech.nilu.explorer

import org.kodein.di.direct
import org.kodein.di.instance
import tech.nilu.explorer.database.DatabaseDriverFactory
import tech.nilu.explorer.di.kodein
import tech.nilu.explorer.entity.Transaction
import tech.nilu.explorer.explorer.Explorer
import tech.nilu.explorer.network.NetworkRepository

class ExplorerSdk(driver: DatabaseDriverFactory) {

    private val repository: NetworkRepository by kodein.di.instance(arg = driver)
    private val explorer: Explorer by lazy {
        val chainId = repository.getActiveNetwork { it.chainId }
        kodein.direct.instance(arg = chainId)
    }

    suspend fun getTransactions(address: String): List<Transaction> =
        when (val response = explorer.getTransactions(address)) {
            is Success -> response.data
            is Error -> emptyList()
        }

    suspend fun getTokenTransactions(
        tokenAddress: String,
        walletAddress: String
    ): List<Transaction> =
        when (val response = explorer.getTokenTransactions(tokenAddress, walletAddress)) {
            is Success -> response.data
            is Error -> emptyList()
        }

    fun canGetTransactions(): Boolean = explorer.canGetTransactions

    fun canGetTokenTransactions(): Boolean = explorer.canGetTokenTransactions
}
