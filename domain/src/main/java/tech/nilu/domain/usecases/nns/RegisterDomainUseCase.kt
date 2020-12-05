package tech.nilu.domain.usecases.nns

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class RegisterDomainUseCase @Inject constructor(
    private val repository: NNSRepository,
    dispatcher: Dispatcher
) : UseCase<RegisterDomainUseCase.Params, Boolean>(dispatcher.io) {

    override suspend fun execute(params: Params): Boolean = repository.registerDomain(
        walletId = params.walletId,
        subnode = params.subnode,
        rootNode = params.rootNode
    )

    data class Params(
        val walletId: Long,
        val subnode: String,
        val rootNode: String
    )
}
