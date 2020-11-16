package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.TransactionRepository
import tech.nilu.web3j.repository.TransactionRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class TransactionModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: TransactionRepositoryImpl): TransactionRepository
}
