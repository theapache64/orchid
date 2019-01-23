package com.tp.orchid.data.repositories

import androidx.lifecycle.LiveData
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.SearchHistoryDao
import com.tp.orchid.data.local.dao.SearchHistoryMovieRelDao
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.*
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OmdbRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiInterface: ApiInterface,
    private val movieDao: MovieDao,
    private val searchHistoryDao: SearchHistoryDao,
    private val searchHistoryMovieRelDao: SearchHistoryMovieRelDao
) {

    /**
     * Search for movies with given keyword
     */
    fun search(keyword: String, page: Int): LiveData<Resource<List<SearchResponse.Movie>>> {

        return object :
            NetworkBoundResource<List<SearchResponse.Movie>, SearchResponse>(appExecutors) {
            override fun saveCallResult(item: SearchResponse) {
                saveSearchResult(keyword, page, item)
            }

            override fun shouldFetch(data: List<SearchResponse.Movie>?): Boolean {
                val searchHistory: SearchHistory? = null
                return data == null || searchHistory == null || searchHistory.isExpired()
            }

            override fun loadFromDb(): LiveData<List<SearchResponse.Movie>> {
                return searchHistoryMovieRelDao.getMovies(keyword, page)
            }

            override fun createCall(): LiveData<ApiResponse<SearchResponse>> {
                return apiInterface.search(keyword, page)
            }


        }.asLiveData()
    }

    private fun saveSearchResult(keyword: String, page: Int, t: SearchResponse) {

        // checking if the keyword and page connected to a search history
        val searchHistory = searchHistoryMovieRelDao.findSearchHistory(keyword, page)

        if (searchHistory != null) {
            // search history connection exist
            return
        }

        if (t.response) {

            // Add search history
            val searchHistoryId = searchHistoryDao
                .insert(
                    SearchHistory(
                        keyword,
                        page,
                        false,
                        null
                    )
                )

            // Adding each movie
            t.movies.forEach { movie ->

                movie.createdAt = Date()

                val dbMovie = movieDao.findMovieByImdbId(movie.imdbId)

                if (dbMovie != null) {
                    // has db
                    searchHistoryMovieRelDao.insert(
                        SearchHistoryMovieRel(
                            searchHistoryId,
                            dbMovie.id
                        )
                    )
                } else {
                    // no db
                    val newMovieId = movieDao.insert(movie)
                    searchHistoryMovieRelDao.insert(
                        SearchHistoryMovieRel(
                            searchHistoryId,
                            newMovieId
                        )
                    )
                }
            }

        } else {

            val errorMessage = "${t.error} ${keyword}"

            searchHistoryDao.insert(
                SearchHistory(
                    keyword,
                    page,
                    true,
                    errorMessage
                )
            )
        }
    }


}