package tech.nilu.domain.usecases.transaction

import kotlinx.coroutines.flow.Flow
import tech.nilu.base.extension.mapSuccess
import tech.nilu.base.result.Result
import tech.nilu.domain.FlowUseCase
import tech.nilu.domain.entity.transaction.ReceiptObject
import tech.nilu.domain.repository.TransactionRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ObserveTransactionReceiptUseCase @Inject constructor(
    private val repository: TransactionRepository,
    dispatcher: Dispatcher
) : FlowUseCase<String, ReceiptObject>(dispatcher.io) {

    override fun execute(params: String): Flow<Result<ReceiptObject>> =
        repository.observeTransactionReceipt(hash = params)
            .mapSuccess()
}
