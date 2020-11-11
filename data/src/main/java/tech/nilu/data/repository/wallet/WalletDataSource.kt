package tech.nilu.data.repository.wallet

import tech.nilu.data.model.Wallet
import javax.inject.Inject

class WalletDataSource @Inject constructor() {

    fun getWallet(id: Long): Wallet = Wallet(id)
}
