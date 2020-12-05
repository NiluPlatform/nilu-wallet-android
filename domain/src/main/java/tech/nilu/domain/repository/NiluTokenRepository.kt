package tech.nilu.domain.repository

import org.web3j.crypto.Credentials
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.domain.entity.erc20.NiluTokenObject
import java.math.BigInteger

interface NiluTokenRepository {

    suspend fun getDeploymentFee(
        credentials: Credentials,
        name: String,
        symbol: String,
        decimals: BigInteger,
        totalSupply: BigInteger
    ): EstimationObject

    suspend fun deployToken(
        credentials: Credentials,
        name: String,
        symbol: String,
        decimals: BigInteger,
        totalSupply: BigInteger,
        estimation: EstimationObject
    ): NiluTokenObject?
}
