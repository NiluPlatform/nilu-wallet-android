package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.entity.ContractInfo
import tech.nilu.domain.entity.contractinfo.ContractObject
import javax.inject.Inject

class ContractInfoMapper @Inject constructor() : Mapper<ContractInfo, ContractObject> {

    override suspend fun map(from: ContractInfo): ContractObject = with(from) {
        ContractObject(
            id = id,
            walletId = walletId,
            address = address,
            name = name,
            image = image,
            types = types,
            symbol = symbol,
            balance = balance,
            tokenName = tokenName,
            tokenSymbol = tokenSymbol,
            tokenTotalSupply = tokenTotalSupply,
            tokenDecimals = tokenDecimals,
            tokenRate = tokenRate,
            tokenIsPayable = tokenIsPayable
        )
    }
}

suspend fun List<ContractInfo>.listMap(
    mapper: suspend (ContractInfo) -> ContractObject
): List<ContractObject> = map { mapper(it) }
