package tech.nilu.api.explorer

import tech.nilu.api.Result
import tech.nilu.api.Success
import tech.nilu.api.entity.Transaction

class EthplorerExplorer : Explorer {

    override val baseUrl: String = "https://api.ethplorer.io/"
    override val canGetTransactions: Boolean = true
    override val canGetTokenTransactions: Boolean = false

    override suspend fun getTransactions(address: String): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): Result<List<Transaction>> =
        Success(emptyList())
}
