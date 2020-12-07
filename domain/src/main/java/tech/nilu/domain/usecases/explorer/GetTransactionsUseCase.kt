package tech.nilu.domain.usecases.explorer

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.transaction.TransactionObject
import tech.nilu.domain.repository.ExplorerRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: ExplorerRepository,
    dispatcher: Dispatcher
) : UseCase<String, List<TransactionObject>>(dispatcher.io) {

    override suspend fun execute(params: String): List<TransactionObject> = repository.getTransactions(address = params)
}
