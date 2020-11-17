package tech.nilu.web3j.repository.wallet

import androidx.collection.LongSparseArray
import tech.nilu.data.dao.ContractInfoDao
import tech.nilu.data.dao.WalletDao
import tech.nilu.web3j.entity.HardWallet
import javax.inject.Inject

class WalletSdk @Inject constructor(
    private val walletDao: WalletDao,
    private val contractInfoDao: ContractInfoDao
) {

    private val wallets by lazy { LongSparseArray<HardWallet>() }
}
