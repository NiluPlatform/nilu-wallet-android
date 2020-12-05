package tech.nilu.domain.repository

import tech.nilu.domain.entity.contractinfo.ContractObject

interface SelectedContractsRepository {

    suspend fun fetchSelectedContracts(walletId: Long): List<ContractObject>

    suspend fun fetchVerifiedContracts(
        walletId: Long,
        offset: Long,
        size: Long
    ): List<ContractObject>
}
