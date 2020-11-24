package tech.nilu.domain.usecases.transfer

import org.web3j.crypto.Credentials
import tech.nilu.domain.UseCase
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.domain.repository.TransferRepository
import tech.nilu.thread.Dispatcher
import java.math.BigDecimal
import javax.inject.Inject

class TransferUseCase @Inject constructor(
    private val repository: TransferRepository,
    dispatcher: Dispatcher
) : UseCase<TransferUseCase.Params, String?>(dispatcher.io) {

    override suspend fun execute(params: Params): String? =
        repository.transfer(
            credentials = params.credentials,
            toAddress = params.toAddress,
            value = params.value,
            estimation = params.estimation,
            contractAddress = params.contractAddress,
            data = params.data
        )

    data class Params(
        val credentials: Credentials,
        val toAddress: String,
        val value: BigDecimal,
        val estimation: EstimationObject,
        val contractAddress: String?,
        val data: String?
    )
}
