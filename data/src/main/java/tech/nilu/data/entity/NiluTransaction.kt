package tech.nilu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class NiluTransaction(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val fromAddress: String,
    val toAddress: String,
    val amount: BigDecimal,
    val time: Date,
    val hash: String
) : BaseEntity
