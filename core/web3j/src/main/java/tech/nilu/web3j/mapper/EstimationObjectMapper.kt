package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.web3j.entity.EstimateValues
import javax.inject.Inject

class EstimationObjectMapper @Inject constructor() : Mapper<EstimationObject, EstimateValues> {

    override suspend fun map(from: EstimationObject): EstimateValues = with(from) {
        EstimateValues(
            nonce = nonce,
            gasPrice = gasPrice,
            gasLimit = gasLimit
        )
    }
}
