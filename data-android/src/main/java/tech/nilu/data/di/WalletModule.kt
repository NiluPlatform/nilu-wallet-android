package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.web3j.repository.wallet.WalletRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class WalletModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: WalletRepositoryImpl): WalletRepository
}
