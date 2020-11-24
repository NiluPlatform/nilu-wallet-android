package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.TransferRepository
import tech.nilu.web3j.repository.transfer.TransferRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class TransferModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: TransferRepositoryImpl): TransferRepository
}
