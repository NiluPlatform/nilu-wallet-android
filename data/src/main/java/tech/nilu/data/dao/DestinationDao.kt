package tech.nilu.data.dao

import androidx.room.Dao
import androidx.room.Query
import tech.nilu.data.entity.Destination

@Dao
abstract class DestinationDao : BaseDao<Destination>() {

    @Query("SELECT * FROM Destination WHERE address = :address LIMIT 1")
    abstract suspend fun getDestination(address: String): Destination?

    @Query("SELECT * FROM Destination")
    abstract suspend fun getDestinations(): List<Destination>
}
