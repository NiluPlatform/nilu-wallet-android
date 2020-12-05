package tech.nilu.domain.usecases.nns

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.DomainInfoObject
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetDomainInfoUseCase @Inject constructor(
    private val repository: NNSRepository,
    dispatcher: Dispatcher
) : UseCase<GetDomainInfoUseCase.Params, DomainInfoObject>(dispatcher.io) {

    override suspend fun execute(params: Params): DomainInfoObject = repository.queryDomains(
        walletId = params.walletId,
        node = params.node
    )

    data class Params(
        val walletId: Long,
        val node: String
    )
}
