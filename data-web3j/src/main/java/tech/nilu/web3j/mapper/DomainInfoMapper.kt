package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.DomainInfoObject
import tech.nilu.web3j.entity.DomainInfo
import javax.inject.Inject

class DomainInfoMapper @Inject constructor() : Mapper<DomainInfo, DomainInfoObject> {

    override suspend fun map(from: DomainInfo): DomainInfoObject = with(from) {
        DomainInfoObject(
            owner = owner,
            resolver = resolver
        )
    }
}
