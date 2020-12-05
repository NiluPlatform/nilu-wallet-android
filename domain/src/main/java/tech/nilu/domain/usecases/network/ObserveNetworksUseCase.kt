package tech.nilu.domain.usecases.network

import kotlinx.coroutines.flow.Flow
import tech.nilu.base.extension.mapSuccess
import tech.nilu.base.result.Result
import tech.nilu.domain.FlowUseCase
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.NetworkRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ObserveNetworksUseCase @Inject constructor(
    private val repository: NetworkRepository,
    dispatcher: Dispatcher
) : FlowUseCase<Unit, List<NetworkObject>>(dispatcher.io) {

    override fun execute(params: Unit): Flow<Result<List<NetworkObject>>> =
        repository.observeNetworks()
            .mapSuccess()
}
