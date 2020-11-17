package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.transaction.TransactionDetailsObject
import tech.nilu.web3j.entity.TransactionDetails
import javax.inject.Inject

class TransactionDetailsMapper @Inject constructor() : Mapper<TransactionDetails, TransactionDetailsObject> {

    override suspend fun map(from: TransactionDetails): TransactionDetailsObject = TransactionDetailsObject(
        nonce = from.transaction?.nonce,
        from = from.transaction?.from,
        to = from.transaction?.to,
        value = from.transaction?.value,
        gasPrice = from.transaction?.gasPrice,
        gas = from.transaction?.gas,
        blockNumber = from.receipt?.blockNumber
    )
}
