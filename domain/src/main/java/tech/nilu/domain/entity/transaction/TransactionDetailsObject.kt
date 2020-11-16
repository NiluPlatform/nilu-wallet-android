package tech.nilu.domain.entity.transaction

import tech.nilu.domain.entity.BaseObject
import java.math.BigInteger

data class TransactionDetailsObject(
    override val id: Long = 0,
    val nonce: BigInteger?,
    val from: String?,
    val to: String?,
    val value: BigInteger?,
    val gasPrice: BigInteger?,
    val gas: BigInteger?,
    val blockNumber: BigInteger?
) : BaseObject
