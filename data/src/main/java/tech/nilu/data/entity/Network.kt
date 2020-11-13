package tech.nilu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Network(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String,
    val address: String,
    val active: Int,
    val symbol: String,
    val chainId: Long,
    val explorerAddress: String
) : BaseEntity {

    override fun toString(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Network
        if (other.address != address) return false

        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
