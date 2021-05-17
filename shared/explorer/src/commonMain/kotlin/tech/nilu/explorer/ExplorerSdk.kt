package tech.nilu.explorer

import org.kodein.di.direct
import org.kodein.di.instance
import tech.nilu.explorer.di.kodein
import tech.nilu.explorer.entity.Transaction
import tech.nilu.explorer.explorer.Explorer

class ExplorerSdk {

    private suspend fun getExplorer(): Explorer =
        kodein.direct.instance()

    suspend fun getTransactions(address: String): List<Transaction> {
        val explorer = getExplorer()
        return when (val response = explorer.getTransactions(address)) {
            is Success -> response.data
            is Error -> emptyList()
        }
    }

    suspend fun getTokenTransactions(
        tokenAddress: String,
        walletAddress: String
    ): List<Transaction> {
        val explorer = getExplorer()
        return when (val response = explorer.getTokenTransactions(tokenAddress, walletAddress)) {
            is Success -> response.data
            is Error -> emptyList()
        }
    }

    suspend fun canGetTransactions(): Boolean = getExplorer().canGetTransactions

    suspend fun canGetTokenTransactions(): Boolean = getExplorer().canGetTokenTransactions
}
