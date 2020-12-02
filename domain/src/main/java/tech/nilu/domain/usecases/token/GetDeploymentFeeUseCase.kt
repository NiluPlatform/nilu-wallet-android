package tech.nilu.domain.usecases.token

import org.web3j.crypto.Credentials
import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.domain.repository.TokenRepository
import tech.nilu.thread.Dispatcher
import java.math.BigInteger
import javax.inject.Inject

class GetDeploymentFeeUseCase @Inject constructor(
    private val repository: TokenRepository,
    dispatcher: Dispatcher
) : UseCase<GetDeploymentFeeUseCase.Params, EstimationObject>(dispatcher.io) {

    override suspend fun execute(params: Params): EstimationObject = repository.getDeploymentFee(
        credentials = params.credentials,
        name = params.name,
        symbol = params.symbol,
        decimals = params.decimals,
        totalSupply = params.totalSupply
    )

    data class Params(
        val credentials: Credentials,
        val name: String,
        val symbol: String,
        val decimals: BigInteger,
        val totalSupply: BigInteger
    )
}
