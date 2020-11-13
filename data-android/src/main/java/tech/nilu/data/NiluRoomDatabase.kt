package tech.nilu.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.nilu.data.entity.Network
import tech.nilu.data.entity.Wallet

@Database(
    entities = [
        Network::class,
        Wallet::class
    ],
    exportSchema = false,
    version = 4
)
@TypeConverters(NiluTypeConverters::class)
abstract class NiluRoomDatabase : RoomDatabase(), NiluDatabase
