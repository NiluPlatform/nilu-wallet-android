package tech.nilu.data.repository.destination

import tech.nilu.data.dao.DestinationDao
import tech.nilu.data.entity.Destination
import javax.inject.Inject

class DestinationDataSource @Inject constructor(
    private val dao: DestinationDao
) {

    suspend fun add(destination: Destination) =
        dao.insert(destination)

    suspend fun getDestination(address: String) =
        dao.getDestination(address)

    suspend fun getDestinations() =
        dao.getDestinations()
}
