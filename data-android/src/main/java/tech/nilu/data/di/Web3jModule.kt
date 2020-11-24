package tech.nilu.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.data.dao.ContractInfoDao
import tech.nilu.data.dao.NetworkDao
import tech.nilu.data.dao.WalletDao
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.repository.wallet.WalletSdk

@InstallIn(ApplicationComponent::class)
@Module
object Web3jModule {

    @Provides
    fun provideWalletSdk(
        walletDao: WalletDao,
        contractInfoDao: ContractInfoDao
    ): WalletSdk = WalletSdk(walletDao, contractInfoDao)

    @Provides
    fun provideWeb3jApiClient(
        networkDao: NetworkDao,
        walletSdk: WalletSdk
    ): Web3jApiClient = Web3jApiClient(networkDao, walletSdk)
}
