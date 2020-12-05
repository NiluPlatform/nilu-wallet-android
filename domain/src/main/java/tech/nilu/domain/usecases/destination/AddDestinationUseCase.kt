package tech.nilu.domain.usecases.destination

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.DestinationObject
import tech.nilu.domain.repository.DestinationRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class AddDestinationUseCase @Inject constructor(
    private val repository: DestinationRepository,
    dispatcher: Dispatcher
) : UseCase<DestinationObject, Long>(dispatcher.io) {

    override suspend fun execute(params: DestinationObject): Long = repository.addDestination(destination = params)
}
