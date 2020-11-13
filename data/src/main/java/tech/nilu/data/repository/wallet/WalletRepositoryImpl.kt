package tech.nilu.data.repository.wallet

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.data.mapper.NetworkToNetworkObject
import tech.nilu.data.mapper.WalletToWalletObject
import tech.nilu.domain.model.NetworkObject
import tech.nilu.domain.model.wallet.WalletObject
import tech.nilu.domain.repository.WalletRepository
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val dataSource: WalletDataSource,
    private val walletMapper: WalletToWalletObject,
    private val networkMapper: NetworkToNetworkObject
) : WalletRepository {

    override suspend fun getWallet(id: Long): WalletObject = walletMapper.map(
        dataSource.getWallet(id)
    )

    override suspend fun getNetwork(id: Long): NetworkObject = networkMapper.map(
        dataSource.getNetwork(id)
    )

    override fun getActiveNetwork(): Flow<NetworkObject> = dataSource.getActiveNetwork()
        .map(networkMapper::map)
}
