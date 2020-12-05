package tech.nilu.domain.usecases.destination

import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.DestinationObject
import tech.nilu.domain.repository.DestinationRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetDestinationUseCase @Inject constructor(
    private val repository: DestinationRepository,
    dispatcher: Dispatcher
) : UseCase<String, DestinationObject?>(dispatcher.io) {

    override suspend fun execute(params: String): DestinationObject? = repository.getDestination(address = params)
}
