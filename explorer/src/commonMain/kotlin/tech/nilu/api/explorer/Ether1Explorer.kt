package tech.nilu.api.explorer

import tech.nilu.api.Result
import tech.nilu.api.entity.Transaction

class Ether1Explorer : Explorer {

    override val baseUrl: String = "https://walletapi.ether1.org/"
    override val canGetTransactions: Boolean = true
    override val canGetTokenTransactions: Boolean = true

    override suspend fun getTransactions(address: String): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): Result<List<Transaction>> {
        TODO("Not yet implemented")
    }
}
