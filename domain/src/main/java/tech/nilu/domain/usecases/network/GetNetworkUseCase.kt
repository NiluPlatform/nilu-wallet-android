package tech.nilu.domain.usecases.network

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetNetworkUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<Long, NetworkObject?>(dispatcher.io) {

    override suspend fun execute(params: Long): NetworkObject? = null
}
