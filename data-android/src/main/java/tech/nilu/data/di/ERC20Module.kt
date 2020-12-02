package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.web3j.repository.erc20.ERC20RepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class ERC20Module {

    @Binds
    @Singleton
    abstract fun bind(implementation: ERC20RepositoryImpl): ERC20Repository
}
