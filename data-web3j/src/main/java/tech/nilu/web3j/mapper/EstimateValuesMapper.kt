package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.EstimationObject
import tech.nilu.web3j.entity.EstimateValues
import javax.inject.Inject

class EstimateValuesMapper @Inject constructor() : Mapper<EstimateValues, EstimationObject> {

    override suspend fun map(from: EstimateValues): EstimationObject = with(from) {
        EstimationObject(
            nonce = nonce,
            gasPrice = gasPrice,
            gasLimit = gasLimit
        )
    }
}
