package tech.nilu.data.repository.wallet

import tech.nilu.data.dao.WalletDao
import tech.nilu.data.entity.Wallet
import javax.inject.Inject

class WalletDataSource @Inject constructor(
    private val dao: WalletDao
) {

    suspend fun add(wallet: Wallet) =
        dao.insert(wallet)

    suspend fun getWallet(id: Long) =
        dao.getWallet(id)

    suspend fun getWallets(networkId: Long) =
        dao.getWallets(networkId)

    fun getWalletsObservable(networkId: Long) =
        dao.getWalletsObservable(networkId)

    suspend fun update(wallet: Wallet) =
        dao.update(wallet)

    suspend fun delete(wallet: Wallet) {
        dao.delete(wallet)
    }
}
