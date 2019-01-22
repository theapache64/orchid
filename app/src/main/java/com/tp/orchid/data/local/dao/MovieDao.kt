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
    fun insert(movie: SearchResponse.Movie): Single<Long>

    @Query("SELECT * FROM movies WHERE imdb_id=:imdbId")
    fun findMovieByImdbId(imdbId: String): Maybe<SearchResponse.Movie>

}