package tech.nilu.domain.entity

data class NetworkObject(
    override val id: Long,
    val name: String,
    val address: String,
    val active: Int,
    val symbol: String,
    val chainId: Long,
    val explorerAddress: String
) : BaseObject
