package tech.nilu.domain.entity

data class DestinationObject(
    override val id: Long,
    val address: String
) : BaseObject
