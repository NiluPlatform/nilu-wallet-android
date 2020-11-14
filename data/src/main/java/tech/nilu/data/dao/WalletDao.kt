package tech.nilu.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.nilu.data.entity.Wallet

@Dao
abstract class WalletDao : BaseDao<Wallet>() {

    @Query("SELECT * FROM Wallet WHERE id = :id")
    abstract suspend fun getWallet(id: Long): Wallet?

    @Query("SELECT * FROM Wallet WHERE networkId = :networkId ORDER BY id")
    abstract suspend fun getWallets(networkId: Long): List<Wallet>

    @Query("SELECT * FROM Wallet WHERE networkId = :networkId ORDER BY id")
    abstract fun getWalletsObservable(networkId: Long): Flow<List<Wallet>>
}
