package tech.nilu.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Transaction
import androidx.room.Update
import tech.nilu.data.entity.BaseEntity

abstract class BaseDao<in E : BaseEntity> {

    @Insert
    abstract suspend fun insert(entity: E): Long

    @Insert
    abstract suspend fun insertAll(vararg entity: E)

    @Insert
    abstract suspend fun insertAll(entities: List<E>)

    @Update
    abstract suspend fun update(entity: E)

    @Delete
    abstract suspend fun delete(entity: E): Int

    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    suspend fun insertOrUpdate(entity: E): Long = if (entity.id == 0L) {
        insert(entity)
    } else {
        update(entity)
        entity.id
    }

    @Transaction
    open suspend fun insertOrUpdate(entities: List<E>) {
        entities.forEach {
            insertOrUpdate(it)
        }
    }
}
