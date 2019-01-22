package com.tp.orchid.data.repositories

import androidx.lifecycle.LiveData
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.SearchHistoryDao
import com.tp.orchid.data.local.dao.SearchHistoryMovieRelDao
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.MovieSearchManager
import com.tp.orchid.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OmdbRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val movieDao: MovieDao,
    private val searchHistoryDao: SearchHistoryDao,
    private val searchHistoryMovieRelDao: SearchHistoryMovieRelDao
) {
    private var searchManager: MovieSearchManager? = null

    /**
     * Search for movies with given keyword
     */
    fun search(keyword: String, page: Int): LiveData<Resource<SearchResponse>> {

        searchManager?.onCleared()

        this.searchManager = MovieSearchManager(
            keyword,
            page,
            apiInterface,
            movieDao,
            searchHistoryDao,
            searchHistoryMovieRelDao
        )

        return searchManager!!.search()
    }

    fun onCleared() {
        searchManager?.onCleared()
    }
}