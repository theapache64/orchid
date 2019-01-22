package com.tp.orchid.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.SearchHistoryDao
import com.tp.orchid.data.local.dao.SearchHistoryMovieRelDao
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.extensions.debug
import com.tp.orchid.utils.extensions.info
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class MovieSearchManager(
    val keyword: String,
    val page: Int,
    val apiInterface: ApiInterface,
    val movieDao: MovieDao,
    val searchHistoryDao: SearchHistoryDao,
    val searchHistoryMovieRelDao: SearchHistoryMovieRelDao
) {


    private val compositeDisposable = CompositeDisposable()
    private lateinit var response: MutableLiveData<Resource<SearchResponse>>

    fun search(): LiveData<Resource<SearchResponse>> {
        debug("Searching for $keyword with page no $page")

        response = MutableLiveData()

        // Checking cache
        searchHistoryDao.findSearchHistory(keyword, page)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    // no cache
                    debug("No cache found")
                    searchWithNetwork()
                },
                onSuccess = { searchHistory ->
                    // cache found
                    debug("Cache found for $keyword")
                    manageCache(searchHistory)
                }
            ).addTo(compositeDisposable)


        return response
    }

    /**
     * Load data from cache
     */
    private fun manageCache(searchHistory: SearchHistory) {
        if (searchHistory.isExpired()) {
            // delete cache
            deleteCacheAndSearchWithNetwork(searchHistory)
        } else {
            // not expired
            if (searchHistory.isFailure) {
                // it was an error
                response.postValue(Resource.error("Cache Says: ${searchHistory.failureReason}"))
            } else {
                // get data from cache
                searchWithCache()
            }
        }
    }

    /**
     * Deletes cache and then search with network
     */
    private fun deleteCacheAndSearchWithNetwork(searchHistory: SearchHistory) {
        Completable
            .fromAction {
                searchHistoryDao.delete(searchHistory)
            }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    // cache deleted, now search with network
                    searchWithNetwork()
                }
            ).addTo(compositeDisposable)
    }

    /**
     * Search with cache
     */
    private fun searchWithCache() {
        searchHistoryMovieRelDao.findMovies(keyword, page)
            .subscribeOn(Schedulers.io())
            .map { movies ->
                movies.map { movie ->
                    movie.title = "C:${movie.title}"
                    movie
                }
            }

            .subscribeBy(
                onNext = { movies ->
                    response.postValue(

                        Resource.success(
                            SearchResponse(movies, 10, null, true)
                        )
                    )
                }
            ).addTo(compositeDisposable)
    }

    private fun searchWithNetwork() {
        // No cache, do network here
        info("Searching with network")
        val observer = getNetworkSearchObserver(response, page, keyword)

        apiInterface.search(keyword, page)
            .subscribeOn(Schedulers.io())
            .subscribe(observer)
    }

    /**
     * Return RxJava network observer
     */
    private fun getNetworkSearchObserver(
        response: MutableLiveData<Resource<SearchResponse>>,
        page: Int,
        keyword: String
    ): SingleObserver<SearchResponse> {

        return object : SingleObserver<SearchResponse> {

            override fun onSuccess(t: SearchResponse) {

                if (t.response) {

                    // Add search history
                    searchHistoryDao
                        .insert(
                            SearchHistory(
                                null,
                                keyword,
                                page,
                                false,
                                null
                            )
                        )
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                            onSuccess = { searchHistoryId ->

                                // Adding each movie
                                t.movies.forEach { movie ->

                                    movie.createdAt = Date()

                                    // checking if the movie already exist
                                    movieDao.findMovieByImdbId(movie.imdbId)
                                        .subscribeOn(Schedulers.io())
                                        .subscribeBy(
                                            onSuccess = {
                                                searchHistoryMovieRelDao.insert(
                                                    SearchHistoryMovieRel(
                                                        null,
                                                        searchHistoryId,
                                                        it.id
                                                    )
                                                )
                                            },
                                            onComplete = {
                                                // movie not found, so adding new movie to the db
                                                movieDao.insert(movie)
                                                    .subscribeBy(
                                                        onSuccess = {
                                                            // Movie added , add the relation also
                                                            searchHistoryMovieRelDao.insert(
                                                                SearchHistoryMovieRel(
                                                                    null,
                                                                    searchHistoryId,
                                                                    it
                                                                )
                                                            )
                                                        }
                                                    )
                                            }
                                        )

                                }

                                // success
                                response.postValue(Resource.success(t))
                            })


                } else {

                    val errorMessage = "${t.error} ${keyword}"
                    addSearchHistoryWithFailure(keyword, page, errorMessage)

                    // error
                    response.postValue(Resource.error(errorMessage))
                }
            }


            override fun onSubscribe(d: Disposable) {
                response.postValue(Resource.loading())

                d.addTo(compositeDisposable)
            }

            override fun onError(e: Throwable) {
                val errorReason = "Seems like a network error : ${e.message}"
                response.postValue(Resource.error(errorReason))
            }

        }
    }

    private fun addSearchHistoryWithFailure(keyword: String, page: Int, failureReason: String) {

        searchHistoryDao.insert(
            SearchHistory(
                null,
                keyword,
                page,
                true,
                failureReason
            )
        )
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun onCleared() {
        // Clearing disposables
        compositeDisposable.clear()
    }

}