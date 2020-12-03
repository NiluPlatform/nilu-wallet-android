package tech.nilu.domain.usecases.network

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.NetworkRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetNetworkUseCase @Inject constructor(
    private val repository: NetworkRepository,
    dispatcher: Dispatcher
) : UseCase<Long, NetworkObject?>(dispatcher.io) {

    override suspend fun execute(params: Long): NetworkObject? = repository.getNetwork(id = params)
}
