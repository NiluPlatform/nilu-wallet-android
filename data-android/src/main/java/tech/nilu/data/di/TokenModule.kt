package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.TokenRepository
import tech.nilu.web3j.repository.erc20.TokenRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class TokenModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: TokenRepositoryImpl): TokenRepository
}
