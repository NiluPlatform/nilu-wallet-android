package tech.nilu.data.repository.wallet

import kotlinx.coroutines.flow.Flow
import tech.nilu.data.dao.NetworkDao
import tech.nilu.data.entity.Network
import tech.nilu.data.model.Wallet
import javax.inject.Inject

class WalletDataSource @Inject constructor(
    private val networkDao: NetworkDao
) {

    fun getWallet(id: Long): Wallet = Wallet(id)

    suspend fun getNetwork(id: Long): Network = networkDao.getNetwork(id)

    fun getActiveNetwork(): Flow<Network> = networkDao.getActiveNetwork()
}
