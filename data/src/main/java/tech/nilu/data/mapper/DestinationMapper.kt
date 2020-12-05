package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.entity.Destination
import tech.nilu.domain.entity.DestinationObject
import javax.inject.Inject

class DestinationMapper @Inject constructor() : Mapper<Destination, DestinationObject> {

    override suspend fun map(from: Destination): DestinationObject = with(from) {
        DestinationObject(
            id = id,
            address = address
        )
    }
}

suspend fun List<Destination>.listMap(
    mapper: suspend (Destination) -> DestinationObject
): List<DestinationObject> = map { mapper(it) }
