package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.entity.NetworkObject

interface NativeNetworkRepository {

    suspend fun addNetworks(networks: List<NetworkObject>)

    fun countNetworks(): Flow<Long>
}
