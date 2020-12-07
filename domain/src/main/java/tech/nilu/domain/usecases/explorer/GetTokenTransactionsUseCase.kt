package tech.nilu.domain.usecases.explorer

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.transaction.TransactionObject
import tech.nilu.domain.repository.ExplorerRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetTokenTransactionsUseCase @Inject constructor(
    private val repository: ExplorerRepository,
    dispatcher: Dispatcher
) : UseCase<GetTokenTransactionsUseCase.Params, List<TransactionObject>>(dispatcher.io) {

    override suspend fun execute(params: Params): List<TransactionObject> = repository.getTokenTransactions(
        tokenAddress = params.tokenAddress,
        walletAddress = params.walletAddress
    )

    data class Params(
        val tokenAddress: String,
        val walletAddress: String
    )
}
