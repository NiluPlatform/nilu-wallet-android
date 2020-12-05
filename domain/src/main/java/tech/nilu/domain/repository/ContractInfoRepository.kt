package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.entity.contractinfo.ContractObject

interface ContractInfoRepository {

    suspend fun getContractInfo(id: Long): ContractObject?

    suspend fun getContractInfos(walletId: Long): List<ContractObject>

    fun observeContractInfos(walletId: Long): Flow<List<ContractObject>>

    suspend fun delete(contract: ContractObject): Int
}
