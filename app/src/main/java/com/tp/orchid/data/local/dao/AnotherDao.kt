package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnotherDao {
    @Query(
        """
 SELECT
                 mv.*,sh.*
                FROM search_histories_movies_rel shmr
                INNER JOIN movies mv ON mv.mv_id = shmr.shmr_movie_id
                INNER JOIN search_histories sh  ON sh.sh_id = shmr.shmr_search_history_id
                WHERE sh.sh_keyword = :keyword AND sh.sh_page = :page GROUP BY mv.mv_imdb_id
    """
    )
    fun getIt(keyword: String, page: Int): LiveData<SearchHistoryWithSearchHistoryMovieRel>
}