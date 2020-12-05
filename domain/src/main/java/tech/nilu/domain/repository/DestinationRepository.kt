package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.entity.DestinationObject

interface DestinationRepository {

    suspend fun addDestination(destination: DestinationObject): Long

    suspend fun getDestination(address: String): DestinationObject?

    fun observeDestinations(): Flow<List<DestinationObject>>
}
