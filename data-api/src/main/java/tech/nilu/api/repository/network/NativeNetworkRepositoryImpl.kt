package tech.nilu.api.repository.network

import kotlinx.coroutines.flow.Flow
import tech.nilu.api.mapper.NetworkObjectMapper
import tech.nilu.api.mapper.listMap
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.NativeNetworkRepository
import tech.nilu.explorer.NetworkSdk
import javax.inject.Inject

class NativeNetworkRepositoryImpl @Inject constructor(
    private val sdk: NetworkSdk,
    private val mapper: NetworkObjectMapper
) : NativeNetworkRepository {

    override suspend fun addNetworks(networks: List<NetworkObject>) =
        sdk.addNetworks(networks.listMap(mapper::map))

    override fun countNetworks(): Flow<Long> =
        sdk.count()
}
