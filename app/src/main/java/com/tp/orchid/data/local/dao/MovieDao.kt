package com.tp.orchid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp.orchid.data.remote.search.SearchResponse
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: SearchResponse.Movie): Long

    @Query("SELECT * FROM movies WHERE mv_imdb_id=:imdbId")
    fun findMovieByImdbId(imdbId: String): SearchResponse.Movie?

}