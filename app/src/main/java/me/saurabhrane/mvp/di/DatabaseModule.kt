package me.saurabhrane.mvp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.saurabhrane.mvp.data.local.AppDatabase
import me.saurabhrane.mvp.data.local.MovieDao
import me.saurabhrane.mvp.data.local.RecentSearchDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Singleton
    @Provides
    fun provideRecentSearchDao(database: AppDatabase): RecentSearchDao {
        return database.queryDao()
    }

}