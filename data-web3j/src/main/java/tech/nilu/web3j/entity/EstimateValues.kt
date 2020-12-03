package tech.nilu.web3j.entity

import java.math.BigInteger

data class EstimateValues(
    val nonce: BigInteger?,
    val gasPrice: BigInteger?,
    val gasLimit: BigInteger?
)
