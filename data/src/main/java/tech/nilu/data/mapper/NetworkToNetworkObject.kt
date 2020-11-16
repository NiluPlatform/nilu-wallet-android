package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.entity.Network
import tech.nilu.domain.entity.NetworkObject
import javax.inject.Inject

class NetworkToNetworkObject @Inject constructor() : Mapper<Network, NetworkObject> {
    override suspend fun map(from: Network): NetworkObject = NetworkObject(
        id = from.chainId
    )
}
