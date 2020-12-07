package tech.nilu.domain.usecases.explorer

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.ExplorerRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class CanGetTransactionsUseCase @Inject constructor(
    private val repository: ExplorerRepository,
    dispatcher: Dispatcher
) : UseCase<Unit, Boolean>(dispatcher.io) {

    override suspend fun execute(params: Unit): Boolean = repository.canGetTokenTransactions()
}
