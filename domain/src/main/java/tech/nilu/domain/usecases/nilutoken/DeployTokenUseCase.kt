package tech.nilu.domain.usecases.nilutoken

import org.web3j.crypto.Credentials
import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.domain.entity.erc20.NiluTokenObject
import tech.nilu.domain.repository.NiluTokenRepository
import tech.nilu.thread.Dispatcher
import java.math.BigInteger
import javax.inject.Inject

class DeployTokenUseCase @Inject constructor(
    private val repository: NiluTokenRepository,
    dispatcher: Dispatcher
) : UseCase<DeployTokenUseCase.Params, NiluTokenObject?>(dispatcher.io) {

    override suspend fun execute(params: Params): NiluTokenObject? = repository.deployToken(
        credentials = params.credentials,
        name = params.name,
        symbol = params.symbol,
        decimals = params.decimals,
        totalSupply = params.totalSupply,
        estimation = params.estimation
    )

    data class Params(
        val credentials: Credentials,
        val name: String,
        val symbol: String,
        val decimals: BigInteger,
        val totalSupply: BigInteger,
        val estimation: EstimationObject
    )
}
