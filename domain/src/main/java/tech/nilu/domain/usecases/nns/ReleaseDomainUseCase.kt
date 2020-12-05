package tech.nilu.domain.usecases.nns

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ReleaseDomainUseCase @Inject constructor(
    private val repository: NNSRepository,
    dispatcher: Dispatcher
) : UseCase<ReleaseDomainUseCase.Params, Boolean>(dispatcher.io) {

    override suspend fun execute(params: Params): Boolean = repository.releaseDomain(
        walletId = params.walletId,
        domainToRelease = params.domainToRelease
    )

    data class Params(
        val walletId: Long,
        val domainToRelease: String
    )
}
