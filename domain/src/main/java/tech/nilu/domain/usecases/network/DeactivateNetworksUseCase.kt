package tech.nilu.domain.usecases.network

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.NetworkRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class DeactivateNetworksUseCase @Inject constructor(
    private val repository: NetworkRepository,
    dispatcher: Dispatcher
) : UseCase<Unit, Unit>(dispatcher.io) {

    override suspend fun execute(params: Unit) = repository.deactivateNetworks()
}
