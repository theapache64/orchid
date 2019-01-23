package com.tp.orchid.di.modules

import android.app.Application
import androidx.room.Room
import com.tp.orchid.data.local.AppDatabase
import com.tp.orchid.data.local.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "orchid.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDao(appDatabase: AppDatabase): SearchHistoryDao {
        return appDatabase.searchHistoryDao()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryMovieRelDao(appDatabase: AppDatabase): SearchHistoryMovieRelDao {
        return appDatabase.searchHistoryMovieRelDao()
    }

    @Singleton
    @Provides
    fun provideMovieDetailsDao(appDatabase: AppDatabase): MovieDetailsDao {
        return appDatabase.movieDetailsDao()
    }


    @Singleton
    @Provides
    fun provideAnotherDao(appDatabase: AppDatabase): AnotherDao {
        return appDatabase.anotherDao()
    }

}