package tech.nilu.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.nilu.data.entity.Network

@Dao
abstract class NetworkDao : BaseDao<Network>() {

    @Query("SELECT * FROM Network WHERE id = :id")
    abstract suspend fun getNetwork(id: Long): Network?

    @Query("SELECT * FROM Network WHERE id = :address")
    abstract suspend fun getNetwork(address: String): Network?

    @Query("SELECT * FROM Network WHERE active = 1 LIMIT 1")
    abstract suspend fun getActiveNetwork(): Network?

    @Query("SELECT * FROM Network WHERE active = 1 LIMIT 1")
    abstract fun getActiveNetworkObservable(): Flow<Network>

    @Query("SELECT * FROM Network")
    abstract suspend fun getNetworks(): List<Network>

    @Query("UPDATE Network SET active = 0")
    abstract suspend fun deactivateNetworks()
}
