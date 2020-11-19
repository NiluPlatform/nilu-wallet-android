package tech.nilu.web3j.mapper

import org.web3j.protocol.core.methods.response.TransactionReceipt
import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.transaction.ReceiptObject
import javax.inject.Inject

class TransactionReceiptMapper @Inject constructor() : Mapper<TransactionReceipt, ReceiptObject> {

    override suspend fun map(from: TransactionReceipt): ReceiptObject = with(from) {
        ReceiptObject(
            from = this.from,
            to = this.to
        )
    }
}
