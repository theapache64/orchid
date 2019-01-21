package com.tp.orchid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel

@Dao
interface SearchHistoryMovieRelDao {

    @Insert
    fun insert(searchHistoryMovieRel: SearchHistoryMovieRel);

    @Query(
        """
            SELECT
                 m.*
                FROM search_histories_movies_rel shmr
                INNER JOIN movies m ON m.id = shmr.movie_id
                INNER JOIN search_histories sh  ON sh.id = shmr.search_history_id
                WHERE sh.keyword = :keyword AND sh.page = :page"""
    )
    fun findMovies(keyword: String, page: Int)
}