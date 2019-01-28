package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tp.orchid.data.remote.search.SearchResponse
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: SearchResponse.Movie): Long

    @Update
    fun update(movie: SearchResponse.Movie)

    @Query("SELECT * FROM movies WHERE mv_imdb_id=:imdbId")
    fun findMovieByImdbId(imdbId: String): SearchResponse.Movie?

    @Query("SELECT * FROM movies WHERE mv_is_fav = 1 GROUP BY mv_imdb_id")
    fun getFavMovies(): LiveData<List<SearchResponse.Movie>>

    @Query(
        """SELECT
            mv.*
            FROM search_histories_movies_rel shmr
            INNER JOIN movies mv ON mv.mv_id = shmr.shmr_movie_id
            INNER JOIN search_histories sh  ON sh.sh_id = shmr.shmr_search_history_id
            WHERE sh.sh_keyword = :keyword AND sh.sh_page = :page GROUP BY mv.mv_id ORDER BY mv.mv_year DESC"""
    )
    fun getMovies(keyword: String, page: Int): LiveData<List<SearchResponse.Movie>>

}