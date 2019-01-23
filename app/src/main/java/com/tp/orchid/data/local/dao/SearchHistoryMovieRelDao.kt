package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel
import com.tp.orchid.data.remote.search.SearchResponse

@Dao
interface SearchHistoryMovieRelDao {

    @Insert
    fun insert(searchHistoryMovieRel: SearchHistoryMovieRel);

    @Query("SELECT * FROM search_histories WHERE sh_keyword=:keyword AND sh_page=:page")
    fun findSearchHistory(keyword: String, page: Int): SearchHistory?

    @Query(
        """
            SELECT
                 mv.*
                FROM search_histories_movies_rel shmr
                INNER JOIN movies mv ON mv.mv_id = shmr.shmr_movie_id
                INNER JOIN search_histories sh  ON sh.sh_id = shmr.shmr_search_history_id
                WHERE sh.sh_keyword = :keyword AND sh.sh_page = :page GROUP BY mv.mv_imdb_id"""
    )
    fun getMovies(keyword: String, page: Int): LiveData<List<SearchResponse.Movie>>
}

data class SearchHistoryWithSearchHistoryMovieRel(


    @Embedded
    var searchHistory: SearchHistory,

    @Relation(parentColumn = "sh_id", entityColumn = "mv_id")
    var movies: List<SearchResponse.Movie>
)