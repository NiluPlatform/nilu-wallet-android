package tech.nilu.data.repository.destination

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.data.mapper.DestinationMapper
import tech.nilu.data.mapper.DestinationObjectMapper
import tech.nilu.data.mapper.listMap
import tech.nilu.domain.entity.DestinationObject
import tech.nilu.domain.repository.DestinationRepository
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(
    private val dataSource: DestinationDataSource,
    private val destinationMapper: DestinationMapper,
    private val destinationObjectMapper: DestinationObjectMapper
) : DestinationRepository {

    override suspend fun addDestination(destination: DestinationObject): Long =
        dataSource.add(destinationObjectMapper.map(destination))

    override suspend fun getDestination(address: String): DestinationObject? =
        dataSource.getDestination(address)?.let { destinationMapper.map(it) }

    override fun observeDestinations(): Flow<List<DestinationObject>> =
        dataSource.getDestinationsObservable()
            .map { it.listMap(destinationMapper::map) }
}
