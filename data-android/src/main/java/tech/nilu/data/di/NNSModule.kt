package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.web3j.repository.smartcontracts.NNSRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class NNSModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: NNSRepositoryImpl): NNSRepository
}
