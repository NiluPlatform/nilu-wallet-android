package tech.nilu.domain.usecases.contractinfo

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ContractInfoRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class DeleteContractInfoUseCase @Inject constructor(
    private val repository: ContractInfoRepository,
    dispatcher: Dispatcher
) : UseCase<ContractObject, Int>(dispatcher.io) {

    override suspend fun execute(params: ContractObject): Int = repository.delete(contract = params)
}
