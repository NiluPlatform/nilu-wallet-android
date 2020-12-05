package tech.nilu.domain.usecases.nns

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ResolveDomainUseCase @Inject constructor(
    private val repository: NNSRepository,
    dispatcher: Dispatcher
) : UseCase<ResolveDomainUseCase.Params, String?>(dispatcher.io) {

    override suspend fun execute(params: Params): String? = repository.resolveDomain(
        walletId = params.walletId,
        domain = params.domain
    )

    data class Params(
        val walletId: Long,
        val domain: String
    )
}
