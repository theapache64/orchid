package com.tp.orchid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.remote.search.SearchResponse

@Database(
    entities = [
        SearchResponse.Movie::class
    ],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}