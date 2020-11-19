package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.transaction.TransactionDetailsObject
import tech.nilu.web3j.entity.TransactionDetails
import javax.inject.Inject

class TransactionDetailsMapper @Inject constructor() : Mapper<TransactionDetails, TransactionDetailsObject> {

    override suspend fun map(from: TransactionDetails): TransactionDetailsObject = with(from) {
        TransactionDetailsObject(
            nonce = transaction?.nonce,
            from = transaction?.from,
            to = transaction?.to,
            value = transaction?.value,
            gasPrice = transaction?.gasPrice,
            gas = transaction?.gas,
            blockNumber = receipt?.blockNumber
        )
    }
}
