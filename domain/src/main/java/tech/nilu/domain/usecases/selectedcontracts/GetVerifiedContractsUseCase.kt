package tech.nilu.domain.usecases.selectedcontracts

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.SelectedContractsRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetVerifiedContractsUseCase @Inject constructor(
    private val repository: SelectedContractsRepository,
    dispatcher: Dispatcher
) : UseCase<GetVerifiedContractsUseCase.Params, List<ContractObject>>(dispatcher.io) {

    override suspend fun execute(params: Params): List<ContractObject> = repository.fetchVerifiedContracts(
        walletId = params.walletId,
        offset = params.offset,
        size = params.size
    )

    data class Params(
        val walletId: Long,
        val offset: Long,
        val size: Long
    )
}
