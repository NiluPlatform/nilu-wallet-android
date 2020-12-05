package tech.nilu.web3j.repository.transaction

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.domain.entity.transaction.ReceiptObject
import tech.nilu.domain.entity.transaction.TransactionDetailsObject
import tech.nilu.domain.repository.TransactionRepository
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.mapper.TransactionDetailsMapper
import tech.nilu.web3j.mapper.TransactionReceiptMapper
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val trxMapper: TransactionDetailsMapper,
    private val receiptMapper: TransactionReceiptMapper
) : TransactionRepository {

    override suspend fun getTransactionDetails(hash: String): TransactionDetailsObject = trxMapper.map(
        client.getTransactionDetails(hash)
    )

    override fun observeTransactionReceipt(hash: String): Flow<ReceiptObject> =
        client.getTransactionReceipt(hash)
            .map(receiptMapper::map)
}
