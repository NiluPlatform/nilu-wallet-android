package tech.nilu.api.explorer

import tech.nilu.api.Result
import tech.nilu.api.entity.Transaction

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
