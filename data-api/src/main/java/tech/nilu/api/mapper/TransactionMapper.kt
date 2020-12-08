package tech.nilu.api.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.transaction.TransactionObject
import tech.nilu.explorer.entity.Transaction
import java.math.BigDecimal
import javax.inject.Inject

class TransactionMapper @Inject constructor() : Mapper<Transaction, TransactionObject> {

    override suspend fun map(from: Transaction): TransactionObject = with(from) {
        TransactionObject(
            id = timestamp,
            timestamp = timestamp,
            from = this.from,
            to = to,
            hash = hash,
            value = BigDecimal(value),
            input = input,
            success = success
        )
    }
}

suspend fun List<Transaction>.listMap(mapper: suspend (Transaction) -> TransactionObject): List<TransactionObject> = map { mapper(it) }
