package tech.nilu.domain.usecases.contractinfo

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ContractInfoRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetContractInfoUseCase @Inject constructor(
    private val repository: ContractInfoRepository,
    dispatcher: Dispatcher
) : UseCase<Long, ContractObject?>(dispatcher.io) {

    override suspend fun execute(params: Long): ContractObject? = repository.getContractInfo(id = params)
}
