package tech.nilu.web3j.repository.transfer

import org.web3j.crypto.Credentials
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.domain.repository.TransferRepository
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.mapper.EstimateValuesMapper
import tech.nilu.web3j.mapper.EstimationObjectMapper
import java.math.BigDecimal
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val estimateValuesMapper: EstimateValuesMapper,
    private val estimationObjectMapper: EstimationObjectMapper
) : TransferRepository {

    override suspend fun getTransferFee(
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        contractAddress: String?,
        data: String?
    ): EstimationObject? = when {
        contractAddress != null -> client.getTransferFee(contractAddress, credentials, toAddress, value)?.let { estimateValuesMapper.map(it) }
        data != null -> client.getTransferFee(credentials, toAddress, value, data)?.let { estimateValuesMapper.map(it) }
        else -> null
    }

    override suspend fun transfer(
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        estimation: EstimationObject,
        contractAddress: String?,
        data: String?
    ): String? = when {
        contractAddress != null -> client.transfer(contractAddress, credentials, toAddress, value, estimationObjectMapper.map(estimation))
        data != null -> client.transfer(credentials, toAddress, value, estimationObjectMapper.map(estimation), data)
        else -> null
    }
}
