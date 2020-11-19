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

class GetWalletWithContractsUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<GetWalletWithContractsUseCase.Params, WalletContractsObject?>(dispatcher.io) {

    override suspend fun execute(params: Params): WalletContractsObject? =
        repository.loadWalletWithContracts(
            walletId = params.walletId,
            parent = params.parent,
            password = params.password
        )

    data class Params(
        val walletId: Long,
        val parent: File,
        val password: String
    )
}
