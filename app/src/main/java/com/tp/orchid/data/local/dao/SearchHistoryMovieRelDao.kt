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

    @Query("SELECT * FROM search_histories WHERE keyword=:keyword AND page=:page")
    fun findSearchHistory(keyword: String, page: Int): SearchHistory?

    @Query(
        """
            SELECT
                 m.*
                FROM search_histories_movies_rel shmr
                INNER JOIN movies m ON m.id = shmr.movie_id
                INNER JOIN search_histories sh  ON sh.id = shmr.search_history_id
                WHERE sh.keyword = :keyword AND sh.page = :page GROUP BY m.imdb_id"""
    )
    fun getMovies(keyword: String, page: Int): LiveData<List<SearchResponse.Movie>>
}

class SearchHistoryWithSearchHistoryMovieRel(
    @Embedded
    var searchHistoryMovieRel: SearchHistoryMovieRel,

    @Relation(parentColumn = "movie_id", entityColumn = "id")
    var movies: List<SearchResponse.Movie>
)