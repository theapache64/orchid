package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tp.orchid.data.remote.search.SearchResponse

@Dao
interface MovieDao {

    @Insert
    fun insertAll(movie: List<SearchResponse.Movie>)

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :keyword || '%'")
    fun loadMovies(keyword: String): LiveData<List<SearchResponse.Movie>>

}