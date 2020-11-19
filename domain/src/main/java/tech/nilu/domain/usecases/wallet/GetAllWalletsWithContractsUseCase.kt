package tech.nilu.domain.usecases.wallet

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.wallet.WalletContractsObject
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import java.io.File
import javax.inject.Inject

/**
 * Created by navid.eghbali on 11/19/20.
 */

class GetAllWalletsWithContractsUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<GetAllWalletsWithContractsUseCase.Params, List<WalletContractsObject>>(dispatcher.io) {

    override suspend fun execute(params: Params): List<WalletContractsObject> =
        repository.loadWalletsWithContracts(
            selectedNetworkId = params.selectedNetworkId,
            parent = params.parent,
            password = params.password
        )

    data class Params(
        val selectedNetworkId: Long,
        val parent: File,
        val password: String
    )
}
