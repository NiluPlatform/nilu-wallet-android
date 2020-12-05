package tech.nilu.domain.usecases.contractinfo

import kotlinx.coroutines.flow.Flow
import tech.nilu.base.extension.mapSuccess
import tech.nilu.base.result.Result
import tech.nilu.domain.FlowUseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ContractInfoRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ObserveContractInfosUseCase @Inject constructor(
    private val repository: ContractInfoRepository,
    dispatcher: Dispatcher
) : FlowUseCase<Long, List<ContractObject>>(dispatcher.io) {

    override fun execute(params: Long): Flow<Result<List<ContractObject>>> =
        repository.observeContractInfos(walletId = params)
            .mapSuccess()
}
