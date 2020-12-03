package tech.nilu.data.repository.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.data.mapper.NetworkMapper
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val mapper: NetworkMapper
) : NetworkRepository {

    override fun getActiveNetwork(): Flow<NetworkObject> =
        dataSource.getActiveNetworkObservable()
            .map(mapper::map)

    override suspend fun getNetwork(id: Long): NetworkObject? =
        dataSource.getNetwork(id)?.let { mapper.map(it) }
}
