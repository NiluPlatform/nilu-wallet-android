package tech.nilu.data.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.model.Wallet
import tech.nilu.domain.model.wallet.WalletObject
import javax.inject.Inject

class WalletToWalletObject @Inject constructor() : Mapper<Wallet, WalletObject> {

    override suspend fun map(from: Wallet): WalletObject = WalletObject(
        id = from.id
    )
}
