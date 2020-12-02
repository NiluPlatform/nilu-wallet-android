package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.entity.ContractInfo
import tech.nilu.domain.entity.contractinfo.ContractObject
import javax.inject.Inject

class ContractObjectMapper @Inject constructor() : Mapper<ContractObject, ContractInfo> {

    override suspend fun map(from: ContractObject): ContractInfo = with(from) {
        ContractInfo(
            id = id,
            walletId = walletId,
            address = address,
            name = name,
            image = image,
            types = types,
            symbol = symbol
        )
    }
}
