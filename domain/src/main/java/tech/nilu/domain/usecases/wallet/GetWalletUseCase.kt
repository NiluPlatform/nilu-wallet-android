package tech.nilu.domain.usecases.wallet

import tech.nilu.domain.UseCase
import tech.nilu.domain.model.wallet.WalletObject
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<Long, WalletObject>(dispatcher.io) {

    override suspend fun execute(params: Long): WalletObject {
        return repository.getWallet(id = params)
    }
}
