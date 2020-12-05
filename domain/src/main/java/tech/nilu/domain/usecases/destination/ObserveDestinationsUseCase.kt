package tech.nilu.domain.usecases.destination

import kotlinx.coroutines.flow.Flow
import tech.nilu.base.extension.mapSuccess
import tech.nilu.base.result.Result
import tech.nilu.domain.FlowUseCase
import tech.nilu.domain.entity.DestinationObject
import tech.nilu.domain.repository.DestinationRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ObserveDestinationsUseCase @Inject constructor(
    private val repository: DestinationRepository,
    dispatcher: Dispatcher
) : FlowUseCase<Unit, List<DestinationObject>>(dispatcher.io) {

    override fun execute(params: Unit): Flow<Result<List<DestinationObject>>> =
        repository.observeDestinations()
            .mapSuccess()
}
