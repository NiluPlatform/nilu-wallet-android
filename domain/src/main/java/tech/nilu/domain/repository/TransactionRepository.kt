package tech.nilu.domain.repository

import tech.nilu.domain.entity.transaction.TransactionDetailsObject

interface TransactionRepository {

    suspend fun getTransactionDetails(hash: String): TransactionDetailsObject
}
