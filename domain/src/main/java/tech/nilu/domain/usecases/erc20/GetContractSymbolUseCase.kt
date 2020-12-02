package tech.nilu.domain.usecases.erc20

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetContractSymbolUseCase @Inject constructor(
    private val repository: ERC20Repository,
    dispatcher: Dispatcher
) : UseCase<GetContractSymbolUseCase.Params, String?>(dispatcher.io) {

    override suspend fun execute(params: Params): String? = repository.contractSymbol(
        walletId = params.walletId,
        contract = params.contract
    )

    data class Params(
        val walletId: Long,
        val contract: ContractObject
    )
}
