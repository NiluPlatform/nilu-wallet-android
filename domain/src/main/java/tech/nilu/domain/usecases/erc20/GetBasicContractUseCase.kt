package tech.nilu.domain.usecases.erc20

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetBasicContractUseCase @Inject constructor(
    private val repository: ERC20Repository,
    dispatcher: Dispatcher
) : UseCase<GetBasicContractUseCase.Params, ContractObject>(dispatcher.io) {

    override suspend fun execute(params: Params): ContractObject = repository.fetchBasicContract(
        walletId = params.walletId,
        contract = params.contract
    )

    data class Params(
        val walletId: Long,
        val contract: ContractObject
    )
}
