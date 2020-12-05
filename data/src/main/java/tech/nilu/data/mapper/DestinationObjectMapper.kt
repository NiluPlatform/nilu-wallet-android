package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.entity.Destination
import tech.nilu.domain.entity.DestinationObject
import javax.inject.Inject

class DestinationObjectMapper @Inject constructor() : Mapper<DestinationObject, Destination> {

    override suspend fun map(from: DestinationObject): Destination = with(from) {
        Destination(
            id = id,
            address = address
        )
    }
}
