package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.data.repository.contractinfo.ContractInfoRepositoryImpl
import tech.nilu.domain.repository.ContractInfoRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class ContractInfoModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: ContractInfoRepositoryImpl): ContractInfoRepository
}
