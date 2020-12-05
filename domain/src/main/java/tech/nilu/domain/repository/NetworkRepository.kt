package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.entity.NetworkObject

interface NetworkRepository {

    suspend fun getNetwork(id: Long): NetworkObject?

    suspend fun getNetwork(address: String): NetworkObject?

    suspend fun getActiveNetwork(): NetworkObject?

    fun observeActiveNetwork(): Flow<NetworkObject>

    suspend fun getNetworks(): List<NetworkObject>

    fun observeNetworks(): Flow<List<NetworkObject>>

    suspend fun deactivateNetworks()

    suspend fun updateNetwork(network: NetworkObject)
}
