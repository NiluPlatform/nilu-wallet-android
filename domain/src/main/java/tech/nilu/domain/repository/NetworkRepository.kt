package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.entity.NetworkObject

interface NetworkRepository {

    fun getActiveNetwork(): Flow<NetworkObject>

    suspend fun getNetwork(id: Long): NetworkObject?
}
