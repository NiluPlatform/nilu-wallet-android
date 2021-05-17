package tech.nilu.explorer.explorer

import tech.nilu.explorer.Result
import tech.nilu.explorer.Success
import tech.nilu.explorer.entity.Transaction

internal class EthplorerExplorer : Explorer {

    override val baseUrl: String = "https://api.ethplorer.io/"
    override val canGetTransactions: Boolean = true
    override val canGetTokenTransactions: Boolean = false

    override suspend fun getTransactions(address: String): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): Result<List<Transaction>> =
        Success(emptyList())
}
