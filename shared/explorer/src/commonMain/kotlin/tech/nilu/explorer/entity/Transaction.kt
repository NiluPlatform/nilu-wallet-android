package tech.nilu.explorer.entity

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val timestamp: Long,
    val from: String,
    val to: String,
    val hash: String,
    val value: String,
    val input: String,
    val success: Boolean
)
