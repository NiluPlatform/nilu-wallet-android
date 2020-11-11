package tech.nilu.data.repository.wallet

import tech.nilu.data.mapper.WalletToWalletObject
import tech.nilu.domain.model.wallet.WalletObject
import tech.nilu.domain.repository.WalletRepository
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val dataSource: WalletDataSource,
    private val mapper: WalletToWalletObject
) : WalletRepository {

    override suspend fun getWallet(id: Long): WalletObject = mapper.map(
        dataSource.getWallet(id)
    )
}
