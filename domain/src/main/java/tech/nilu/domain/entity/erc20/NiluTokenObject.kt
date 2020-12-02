package tech.nilu.domain.entity.erc20

import java.math.BigInteger

data class NiluTokenObject(
    val contractAddress: String,
    val transactionHash: String?,
    val blockNumber: BigInteger?,
    val from: String?,
    val gasUsed: BigInteger?
)
