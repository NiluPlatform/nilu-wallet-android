package tech.nilu.web3j.repository

import tech.nilu.domain.entity.transaction.TransactionDetailsObject
import tech.nilu.domain.repository.TransactionRepository
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.mapper.TransactionDetailsToTransactionDetailsObject
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val mapper: TransactionDetailsToTransactionDetailsObject
) : TransactionRepository {

    override suspend fun getTransactionDetails(hash: String): TransactionDetailsObject = mapper.map(
        client.getTransactionDetails(hash)
    )
}
