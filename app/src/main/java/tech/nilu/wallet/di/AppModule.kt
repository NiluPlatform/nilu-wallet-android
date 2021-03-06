package tech.nilu.wallet.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import tech.nilu.explorer.AndroidNetworkSdk
import tech.nilu.explorer.ExplorerSdk
import tech.nilu.explorer.NetworkSdk
import tech.nilu.explorer.database.DatabaseDriverFactory
import tech.nilu.thread.Dispatcher
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module(includes = [])
object AppModule {

    @Singleton
    @Provides
    fun provideDispatcher() = Dispatcher(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Singleton
    @Provides
    fun provideDatabaseDriverFactory(app: Application) = DatabaseDriverFactory(app)

    @Singleton
    @Provides
    fun provideExplorerSdk(driver: DatabaseDriverFactory) = ExplorerSdk(driver)

    @Singleton
    @Provides
    fun provideNetworkSdk(driver: DatabaseDriverFactory): NetworkSdk = AndroidNetworkSdk(driver)
}
