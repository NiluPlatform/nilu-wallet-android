package tech.nilu.domain.usecases.network

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.NativeNetworkRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class AddNetworksUseCase @Inject constructor(
    private val repository: NativeNetworkRepository,
    dispatcher: Dispatcher
) : UseCase<List<NetworkObject>, Unit>(dispatcher.io) {

    override suspend fun execute(params: List<NetworkObject>) = repository.addNetworks(networks = params)
}
