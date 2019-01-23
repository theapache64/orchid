package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp.orchid.data.remote.get_movie.GetMovieResponse

@Dao
interface MovieDetailsDao {
    @Insert
    fun insert(getMovieResponse: GetMovieResponse)

    @Query("SELECT * FROM movie_details WHERE movie_id=:movieId LIMIT 1")
    fun getMovieDetails(movieId: Long): LiveData<GetMovieResponse>
}