package com.tp.theah64.orchidexample.di.modules

import android.app.Application
import androidx.room.Room
import com.tp.theah64.orchidexample.data.local.AppDatabase
import com.tp.theah64.orchidexample.data.local.dao.MovieDao
import com.tp.theah64.orchidexample.data.local.dao.MovieDetailsDao
import com.tp.theah64.orchidexample.data.local.dao.SearchHistoryDao
import com.tp.theah64.orchidexample.data.local.dao.SearchHistoryMovieRelDao
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
}