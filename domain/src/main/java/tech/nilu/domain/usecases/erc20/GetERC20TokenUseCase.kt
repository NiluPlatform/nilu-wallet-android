package tech.nilu.domain.usecases.erc20

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetERC20TokenUseCase @Inject constructor(
    private val repository: ERC20Repository,
    dispatcher: Dispatcher
) : UseCase<GetERC20TokenUseCase.Params, ContractObject>(dispatcher.io) {

    override suspend fun execute(params: Params): ContractObject = repository.fetchERC20Token(
        walletId = params.walletId,
        contract = params.contract
    )

    data class Params(
        val walletId: Long,
        val contract: ContractObject
    )
}
