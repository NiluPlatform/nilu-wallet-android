package tech.nilu.api

import org.kodein.di.instance
import tech.nilu.api.di.kodein
import tech.nilu.api.entity.Transaction
import tech.nilu.api.explorer.Explorer

class ExplorerSdk {

    private val explorer: Explorer by kodein.di.instance(arg = 512L)

    suspend fun getTransactions(address: String): List<Transaction> = when (val response = explorer.getTransactions(address)) {
        is Success -> response.data
        is Error -> emptyList()
    }

    suspend fun getTokenTransactions(
        tokenAddress: String,
        walletAddress: String
    ): List<Transaction> = when (val response = explorer.getTokenTransactions(tokenAddress, walletAddress)) {
        is Success -> response.data
        is Error -> emptyList()
    }

    fun canGetTransactions(): Boolean = explorer.canGetTransactions

    fun canGetTokenTransactions(): Boolean = explorer.canGetTokenTransactions
}
