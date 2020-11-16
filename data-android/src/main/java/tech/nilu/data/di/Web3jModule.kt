package tech.nilu.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.data.dao.NetworkDao
import tech.nilu.web3j.Web3jApiClient

@InstallIn(ApplicationComponent::class)
@Module
object Web3jModule {

    @Provides
    fun provide(networkDao: NetworkDao): Web3jApiClient = Web3jApiClient(networkDao)
}
