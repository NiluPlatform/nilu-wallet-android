package tech.nilu.api.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.explorer.database.Network
import javax.inject.Inject

class NetworkObjectMapper @Inject constructor() : Mapper<NetworkObject, Network> {

    override suspend fun map(from: NetworkObject): Network = with(from) {
        Network(
            id = id,
            name = name,
            address = address,
            active = active.toLong(),
            symbol = symbol,
            chainId = chainId,
            explorerAddress = explorerAddress
        )
    }
}

suspend fun List<NetworkObject>.listMap(
    mapper: suspend (NetworkObject) -> Network
): List<Network> = map { mapper(it) }
