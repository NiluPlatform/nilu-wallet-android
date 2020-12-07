package tech.nilu.domain.repository

import tech.nilu.domain.entity.transaction.TransactionObject

interface ExplorerRepository {

    suspend fun getTransactions(address: String): List<TransactionObject>

    suspend fun getTokenTransactions(
        tokenAddress: String,
        walletAddress: String
    ): List<TransactionObject>

    fun canGetTransactions(): Boolean

    fun canGetTokenTransactions(): Boolean
}
