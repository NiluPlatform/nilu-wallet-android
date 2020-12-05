package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.entity.transaction.ReceiptObject
import tech.nilu.domain.entity.transaction.TransactionDetailsObject

interface TransactionRepository {

    suspend fun getTransactionDetails(hash: String): TransactionDetailsObject

    fun observeTransactionReceipt(hash: String): Flow<ReceiptObject>
}
