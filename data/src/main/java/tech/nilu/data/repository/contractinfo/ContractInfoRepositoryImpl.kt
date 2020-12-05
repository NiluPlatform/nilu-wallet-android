package tech.nilu.data.repository.contractinfo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.data.mapper.ContractInfoMapper
import tech.nilu.data.mapper.ContractObjectMapper
import tech.nilu.data.mapper.listMap
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ContractInfoRepository
import javax.inject.Inject

class ContractInfoRepositoryImpl @Inject constructor(
    private val dataSource: ContractInfoDataSource,
    private val contractInfoMapper: ContractInfoMapper,
    private val contractObjectMapper: ContractObjectMapper
) : ContractInfoRepository {

    override suspend fun getContractInfo(id: Long): ContractObject? =
        dataSource.getContractInfo(id)?.let { contractInfoMapper.map(it) }

    override suspend fun getContractInfos(walletId: Long): List<ContractObject> =
        dataSource.getContractInfos(walletId)
            .listMap(contractInfoMapper::map)

    override fun observeContractInfos(walletId: Long): Flow<List<ContractObject>> =
        dataSource.getContractInfosObservable(walletId)
            .map { it.listMap(contractInfoMapper::map) }

    override suspend fun delete(contract: ContractObject): Int =
        dataSource.delete(contractObjectMapper.map(contract))
}
