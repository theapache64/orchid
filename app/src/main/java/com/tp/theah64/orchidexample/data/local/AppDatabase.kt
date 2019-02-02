package com.tp.theah64.orchidexample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tp.theah64.orchid.data.local.typeconverters.DateConverter
import com.tp.theah64.orchidexample.data.local.dao.MovieDao
import com.tp.theah64.orchidexample.data.local.dao.MovieDetailsDao
import com.tp.theah64.orchidexample.data.local.dao.SearchHistoryDao
import com.tp.theah64.orchidexample.data.local.dao.SearchHistoryMovieRelDao
import com.tp.theah64.orchidexample.data.local.entities.SearchHistory
import com.tp.theah64.orchidexample.data.local.entities.SearchHistoryMovieRel
import com.tp.theah64.orchidexample.data.local.typeconverters.RatingsConverter
import com.tp.theah64.orchidexample.data.remote.getmovie.GetMovieResponse
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse

@Database(
    entities = [
        SearchHistoryMovieRel::class,
        SearchResponse.Movie::class,
        SearchHistory::class,
        GetMovieResponse::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    DateConverter::class,
    RatingsConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun searchHistoryMovieRelDao(): SearchHistoryMovieRelDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}