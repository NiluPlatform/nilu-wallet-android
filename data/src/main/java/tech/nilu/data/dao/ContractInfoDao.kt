package tech.nilu.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.nilu.data.entity.ContractInfo

@Dao
abstract class ContractInfoDao : BaseDao<ContractInfo>() {

    @Query("SELECT * FROM ContractInfo WHERE id = :id")
    abstract suspend fun getContractInfo(id: Long): ContractInfo?

    @Query("SELECT * FROM ContractInfo WHERE walletId = :walletId")
    abstract suspend fun getContractInfos(walletId: Long): List<ContractInfo>

    @Query("SELECT * FROM ContractInfo WHERE walletId = :walletId")
    abstract fun getContractInfosObservable(walletId: Long): Flow<List<ContractInfo>>

    @Query("DELETE FROM ContractInfo WHERE walletId = :walletId")
    abstract suspend fun deleteContractInfos(walletId: Long)
}
