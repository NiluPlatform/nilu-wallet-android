package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.domain.entity.erc20.NiluTokenObject
import tech.nilu.web3j.repository.smartcontracts.templates.NiluToken
import javax.inject.Inject

class NiluTokenMapper @Inject constructor() : Mapper<NiluToken, NiluTokenObject> {

    override suspend fun map(from: NiluToken): NiluTokenObject = with(from) {
        NiluTokenObject(
            contractAddress = contractAddress,
            transactionHash = if (transactionReceipt.isPresent) transactionReceipt.get().transactionHash else null,
            blockNumber = if (transactionReceipt.isPresent) transactionReceipt.get().blockNumber else null,
            from = if (transactionReceipt.isPresent) transactionReceipt.get().from else null,
            gasUsed = if (transactionReceipt.isPresent) transactionReceipt.get().gasUsed else null
        )
    }
}
