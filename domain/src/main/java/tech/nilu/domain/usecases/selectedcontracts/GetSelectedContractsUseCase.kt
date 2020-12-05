package tech.nilu.domain.usecases.selectedcontracts

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.SelectedContractsRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetSelectedContractsUseCase @Inject constructor(
    private val repository: SelectedContractsRepository,
    dispatcher: Dispatcher
) : UseCase<Long, List<ContractObject>>(dispatcher.io) {

    override suspend fun execute(params: Long): List<ContractObject> = repository.fetchSelectedContracts(
        walletId = params
    )
}
