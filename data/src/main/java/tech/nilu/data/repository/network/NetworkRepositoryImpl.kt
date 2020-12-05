package tech.nilu.data.repository.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.data.mapper.NetworkMapper
import tech.nilu.data.mapper.NetworkObjectMapper
import tech.nilu.data.mapper.listMap
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val networkMapper: NetworkMapper,
    private val networkObjectMapper: NetworkObjectMapper
) : NetworkRepository {

    override suspend fun getNetwork(id: Long): NetworkObject? =
        dataSource.getNetwork(id)?.let { networkMapper.map(it) }

    override suspend fun getNetwork(address: String): NetworkObject? =
        dataSource.getNetwork(address)?.let { networkMapper.map(it) }

    override suspend fun getActiveNetwork(): NetworkObject? =
        dataSource.getActiveNetwork()?.let { networkMapper.map(it) }

    override fun observeActiveNetwork(): Flow<NetworkObject> =
        dataSource.getActiveNetworkObservable()
            .map(networkMapper::map)

    override suspend fun getNetworks(): List<NetworkObject> =
        dataSource.getNetworks()
            .listMap(networkMapper::map)

    override fun observeNetworks(): Flow<List<NetworkObject>> =
        dataSource.getNetworksObservable()
            .map { it.listMap(networkMapper::map) }

    override suspend fun deactivateNetworks() =
        dataSource.deactivateNetworks()

    override suspend fun updateNetwork(network: NetworkObject) =
        dataSource.update(networkObjectMapper.map(network))
}
