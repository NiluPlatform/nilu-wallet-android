package tech.nilu.data.dao

import androidx.room.Dao
import tech.nilu.data.entity.NiluTransaction

@Dao
abstract class TransactionDao : BaseDao<NiluTransaction>()
