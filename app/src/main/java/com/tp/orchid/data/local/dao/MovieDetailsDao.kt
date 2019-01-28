package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp.orchid.data.remote.getmovie.GetMovieResponse

@Dao
interface MovieDetailsDao {
    @Insert
    fun insert(getMovieResponse: GetMovieResponse)

    @Query(
        """
        SELECT
        md.*
        FROM movie_details md
        INNER JOIN movies mv ON mv.mv_id = md.movie_id
        WHERE mv.mv_imdb_id = :imdbId
    """
    )
    fun getMovieDetails(imdbId: String): LiveData<GetMovieResponse>
}