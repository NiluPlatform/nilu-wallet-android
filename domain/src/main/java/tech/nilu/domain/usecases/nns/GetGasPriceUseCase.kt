package tech.nilu.domain.usecases.nns

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.thread.Dispatcher
import java.math.BigInteger
import javax.inject.Inject

class GetGasPriceUseCase @Inject constructor(
    private val repository: NNSRepository,
    dispatcher: Dispatcher
) : UseCase<Unit, BigInteger?>(dispatcher.io) {

    override suspend fun execute(params: Unit): BigInteger? = repository.getGasPrice()
}
