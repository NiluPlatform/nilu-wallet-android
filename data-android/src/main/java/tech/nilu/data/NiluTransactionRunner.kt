package tech.nilu.data

import androidx.room.withTransaction
import javax.inject.Inject

class NiluTransactionRunner @Inject constructor(
    private val db: NiluRoomDatabase
) : DatabaseTransactionRunner {

    override suspend fun <T> invoke(block: suspend () -> T): T = db.withTransaction {
        block()
    }
}
