package tech.nilu.domain.usecases.transaction

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.transaction.TransactionDetailsObject
import tech.nilu.domain.repository.TransactionRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetTransactionDetailsUseCase @Inject constructor(
    private val repository: TransactionRepository,
    dispatcher: Dispatcher
) : UseCase<String, TransactionDetailsObject>(dispatcher.io) {

    override suspend fun execute(params: String): TransactionDetailsObject =
        repository.getTransactionDetails(hash = params)
}
