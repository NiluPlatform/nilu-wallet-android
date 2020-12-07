package tech.nilu.domain.entity.transaction

import tech.nilu.domain.entity.BaseObject
import java.math.BigDecimal

data class TransactionObject(
    override val id: Long,
    val timestamp: Long,
    val from: String,
    val to: String,
    val hash: String,
    val value: BigDecimal,
    val input: String,
    val success: Boolean
) : BaseObject
