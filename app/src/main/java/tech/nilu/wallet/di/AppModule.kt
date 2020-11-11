package tech.nilu.wallet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
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
}
