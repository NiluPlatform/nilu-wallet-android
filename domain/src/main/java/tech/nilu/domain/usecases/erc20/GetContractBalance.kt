package tech.nilu.domain.usecases.erc20

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.thread.Dispatcher
import java.math.BigInteger
import javax.inject.Inject

class GetContractBalance @Inject constructor(
    private val repository: ERC20Repository,
    dispatcher: Dispatcher
) : UseCase<GetContractBalance.Params, BigInteger?>(dispatcher.io) {

    override suspend fun execute(params: Params): BigInteger? = repository.contractBalance(
        walletId = params.walletId,
        contract = params.contract
    )

    data class Params(
        val walletId: Long,
        val contract: ContractObject
    )
}
