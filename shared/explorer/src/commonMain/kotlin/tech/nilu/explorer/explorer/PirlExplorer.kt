package tech.nilu.explorer.explorer

import tech.nilu.explorer.Result
import tech.nilu.explorer.entity.Transaction

class PirlExplorer : Explorer {

    override val baseUrl: String = "http://devpool.nilu.tech/pirl/"
    override val canGetTransactions: Boolean = true
    override val canGetTokenTransactions: Boolean = true

    override suspend fun getTransactions(address: String): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }
}
