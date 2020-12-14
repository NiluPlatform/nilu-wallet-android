package tech.nilu.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.api.repository.network.NativeNetworkRepositoryImpl
import tech.nilu.data.repository.network.NetworkRepositoryImpl
import tech.nilu.domain.repository.NativeNetworkRepository
import tech.nilu.domain.repository.NetworkRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkRepository(implementation: NetworkRepositoryImpl): NetworkRepository

    @Binds
    @Singleton
    abstract fun bindNativeNetworkRepository(implementation: NativeNetworkRepositoryImpl): NativeNetworkRepository
}
