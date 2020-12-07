package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.api.repository.explorer.ExplorerRepositoryImpl
import tech.nilu.domain.repository.ExplorerRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class ExplorerModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: ExplorerRepositoryImpl): ExplorerRepository
}
