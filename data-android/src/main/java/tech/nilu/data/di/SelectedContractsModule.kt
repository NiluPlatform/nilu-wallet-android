package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.SelectedContractsRepository
import tech.nilu.web3j.repository.smartcontracts.SelectedContractsRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class SelectedContractsModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: SelectedContractsRepositoryImpl): SelectedContractsRepository
}
