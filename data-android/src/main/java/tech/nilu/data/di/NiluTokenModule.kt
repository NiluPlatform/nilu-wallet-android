package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.NiluTokenRepository
import tech.nilu.web3j.repository.smartcontracts.NiluTokenRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class NiluTokenModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: NiluTokenRepositoryImpl): NiluTokenRepository
}
