package tech.nilu.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.nilu.data.entity.Network

@Dao
abstract class NetworkDao : BaseDao<Network>() {

    @Query("SELECT * FROM Network WHERE id = :id")
    abstract suspend fun getNetwork(id: Long): Network

    @Query("SELECT * FROM Network WHERE active = 1 LIMIT 1")
    abstract fun getActiveNetwork(): Flow<Network>
}
