package tech.nilu.domain.entity.transaction

import tech.nilu.domain.entity.BaseObject

data class ReceiptObject(
    override val id: Long = 0,
    val from: String?,
    val to: String?
) : BaseObject
