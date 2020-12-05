package tech.nilu.web3j.repository.smartcontracts

import tech.nilu.data.mapper.ContractInfoMapper
import tech.nilu.data.mapper.listMap
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.SelectedContractsRepository
import tech.nilu.web3j.Web3jApiClient
import javax.inject.Inject

class SelectedContractsRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val mapper: ContractInfoMapper
) : SelectedContractsRepository {

    override suspend fun fetchSelectedContracts(walletId: Long): List<ContractObject> =
        client.fetchSelectedContracts(walletId)
            .listMap(mapper::map)

    override suspend fun fetchVerifiedContracts(walletId: Long, offset: Long, size: Long): List<ContractObject> =
        client.fetchVerifiedContracts(walletId, offset, size)
            .listMap(mapper::map)
}
