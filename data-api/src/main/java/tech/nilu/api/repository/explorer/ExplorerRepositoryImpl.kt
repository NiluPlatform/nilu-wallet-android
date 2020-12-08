package tech.nilu.api.repository.explorer

import tech.nilu.api.mapper.TransactionMapper
import tech.nilu.api.mapper.listMap
import tech.nilu.domain.entity.transaction.TransactionObject
import tech.nilu.domain.repository.ExplorerRepository
import tech.nilu.explorer.ExplorerSdk
import javax.inject.Inject

class ExplorerRepositoryImpl @Inject constructor(
    private val sdk: ExplorerSdk,
    private val mapper: TransactionMapper
) : ExplorerRepository {

    override suspend fun getTransactions(address: String): List<TransactionObject> =
        sdk.getTransactions(address)
            .listMap(mapper::map)

    override suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): List<TransactionObject> =
        sdk.getTokenTransactions(tokenAddress, walletAddress)
            .listMap(mapper::map)

    override fun canGetTransactions(): Boolean = sdk.canGetTransactions()

    override fun canGetTokenTransactions(): Boolean = sdk.canGetTokenTransactions()
}
