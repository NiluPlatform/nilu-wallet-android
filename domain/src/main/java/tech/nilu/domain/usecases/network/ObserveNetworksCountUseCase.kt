package tech.nilu.domain.usecases.network

import kotlinx.coroutines.flow.Flow
import tech.nilu.base.extension.mapSuccess
import tech.nilu.base.result.Result
import tech.nilu.domain.FlowUseCase
import tech.nilu.domain.repository.NativeNetworkRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ObserveNetworksCountUseCase @Inject constructor(
    private val repository: NativeNetworkRepository,
    dispatcher: Dispatcher
) : FlowUseCase<Unit, Long>(dispatcher.io) {

    override fun execute(params: Unit): Flow<Result<Long>> =
        repository.countNetworks()
            .mapSuccess()
}
