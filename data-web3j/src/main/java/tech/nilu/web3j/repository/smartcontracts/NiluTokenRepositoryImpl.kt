package tech.nilu.web3j.repository.smartcontracts

import org.web3j.crypto.Credentials
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.domain.entity.erc20.NiluTokenObject
import tech.nilu.domain.repository.NiluTokenRepository
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.mapper.EstimateValuesMapper
import tech.nilu.web3j.mapper.EstimationObjectMapper
import tech.nilu.web3j.mapper.NiluTokenMapper
import java.math.BigInteger
import javax.inject.Inject

class NiluTokenRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val estimateValuesMapper: EstimateValuesMapper,
    private val estimationObjectMapper: EstimationObjectMapper,
    private val niluTokenMapper: NiluTokenMapper
) : NiluTokenRepository {

    override suspend fun getDeploymentFee(
        credentials: Credentials,
        name: String,
        symbol: String,
        decimals: BigInteger,
        totalSupply: BigInteger
    ): EstimationObject =
        estimateValuesMapper.map(client.getDeploymentFee(credentials, name, symbol, decimals, totalSupply))

    override suspend fun deployToken(
        credentials: Credentials,
        name: String,
        symbol: String,
        decimals: BigInteger,
        totalSupply: BigInteger,
        estimation: EstimationObject
    ): NiluTokenObject? =
        client.deployToken(credentials, name, symbol, decimals, totalSupply, estimationObjectMapper.map(estimation))?.let {
            niluTokenMapper.map(it)
        }
}
