package tech.nilu.domain.usecases.erc20

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class SaveContractsUseCase @Inject constructor(
    private val repository: ERC20Repository,
    dispatcher: Dispatcher
) : UseCase<SaveContractsUseCase.Params, Unit>(dispatcher.io) {

    override suspend fun execute(params: Params) = repository.saveContracts(
        walletId = params.walletId,
        contracts = params.contracts
    )

    data class Params(
        val walletId: Long,
        val contracts: List<ContractObject>
    )
}
