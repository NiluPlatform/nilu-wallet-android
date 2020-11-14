package tech.nilu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Destination(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val address: String
) : BaseEntity {

    override fun toString(): String = address
}
