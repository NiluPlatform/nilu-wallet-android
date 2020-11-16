package tech.nilu.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import tech.nilu.data.*
import tech.nilu.thread.ioThread
import java.io.InputStreamReader
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provide(app: Application): NiluRoomDatabase =
        Room.databaseBuilder(app, NiluRoomDatabase::class.java, "nilu.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        InputStreamReader(app.assets.open("nilu.sql")).buffered().forEachLine {
                            db.execSQL(it)
                        }
                    }
                }
            })
            .addMigrations(*Migrations.allMigrations)
            .fallbackToDestructiveMigration()
            .build()
}

@InstallIn(ApplicationComponent::class)
@Module
object DaoModule {

    @Provides
    fun provideNetworkDao(db: NiluDatabase) = db.networkDao()
}

@InstallIn(ApplicationComponent::class)
@Module
abstract class DatabaseTransactionModule {

    @Binds
    abstract fun bindNiluDatabase(database: NiluRoomDatabase): NiluDatabase

    @Binds
    @Singleton
    abstract fun bindDatabaseTransactionRunner(runner: NiluTransactionRunner): DatabaseTransactionRunner
}
