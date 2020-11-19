package tech.nilu.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("networkId")],
    foreignKeys = [ForeignKey(entity = Network::class, parentColumns = ["id"], childColumns = ["networkId"])]
)
data class Wallet(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String,
    val path: String,
    val mnemonic: String?,
    val networkId: Long
) : BaseEntity
