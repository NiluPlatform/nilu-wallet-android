package tech.nilu.domain.entity

import java.math.BigInteger

data class EstimationObject(
    val nonce: BigInteger?,
    val gasPrice: BigInteger?,
    val gasLimit: BigInteger?
)
