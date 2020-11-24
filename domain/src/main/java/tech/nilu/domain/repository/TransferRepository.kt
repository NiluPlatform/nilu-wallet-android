package tech.nilu.domain.repository

import org.web3j.crypto.Credentials
import tech.nilu.domain.entity.EstimationObject
import java.math.BigDecimal

interface TransferRepository {

    suspend fun getTransferFee(
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        contractAddress: String?,
        data: String?
    ): EstimationObject?

    suspend fun transfer(
        credentials: Credentials,
        toAddress: String,
        value: BigDecimal,
        estimation: EstimationObject,
        contractAddress: String?,
        data: String?
    ): String?
}
