package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.entity.Network
import tech.nilu.domain.entity.NetworkObject
import javax.inject.Inject

class NetworkObjectMapper @Inject constructor() : Mapper<NetworkObject, Network> {

    override suspend fun map(from: NetworkObject): Network = with(from) {
        Network(
            id = id,
            name = name,
            address = address,
            active = active,
            symbol = symbol,
            chainId = chainId,
            explorerAddress = explorerAddress
        )
    }
}
