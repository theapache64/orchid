package com.tp.orchid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tp.orchid.data.local.dao.*
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel
import com.tp.orchid.data.local.typeconverters.DateConverter
import com.tp.orchid.data.local.typeconverters.RatingsConverter
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.data.remote.get_movie.GetMovieResponse

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