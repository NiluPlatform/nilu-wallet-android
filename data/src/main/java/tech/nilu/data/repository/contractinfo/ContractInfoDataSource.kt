package tech.nilu.data.repository.contractinfo

import tech.nilu.data.dao.ContractInfoDao
import tech.nilu.data.entity.ContractInfo
import javax.inject.Inject

class ContractInfoDataSource @Inject constructor(
    private val dao: ContractInfoDao
) {

    suspend fun add(contract: ContractInfo) =
        dao.insert(contract)

    suspend fun getContractInfo(id: Long) =
        dao.getContractInfo(id)

    suspend fun getContractInfos(walletId: Long) =
        dao.getContractInfos(walletId)

    fun getContractInfoObservable(walletId: Long) =
        dao.getContractInfosObservable(walletId)

    suspend fun delete(contract: ContractInfo) =
        dao.delete(contract)

    suspend fun delete(walletId: Long) =
        dao.deleteContractInfos(walletId)
}
