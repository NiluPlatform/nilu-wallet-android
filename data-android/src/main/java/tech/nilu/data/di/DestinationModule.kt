package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.data.repository.destination.DestinationRepositoryImpl
import tech.nilu.domain.repository.DestinationRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class DestinationModule {

    @Binds
    @Singleton
    abstract fun bind(implementation: DestinationRepositoryImpl): DestinationRepository
}
