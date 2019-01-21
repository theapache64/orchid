package com.tp.orchid.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.SearchHistoryDao
import com.tp.orchid.data.local.dao.SearchHistoryMovieRelDao
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.debug
import com.tp.orchid.utils.extensions.error
import com.tp.orchid.utils.extensions.info
import io.reactivex.Completable
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OmdbRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val movieDao: MovieDao,
    val searchHistoryDao: SearchHistoryDao,
    val searchHistoryMovieRelDao: SearchHistoryMovieRelDao
) {

    /**
     * Search for movies with given keyword
     */
    fun search(keyword: String, page: Int): LiveData<Resource<SearchResponse>> {

        val response = MutableLiveData<Resource<SearchResponse>>()

        // Checking cache
        searchHistoryDao.findSearchHistory(keyword, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFindSearchHistoryObserver(response, page, keyword))

        return response
    }

    private fun getFindSearchHistoryObserver(
        response: MutableLiveData<Resource<SearchResponse>>,
        page: Int,
        keyword: String
    ): MaybeObserver<SearchHistory?> {

        return object : MaybeObserver<SearchHistory?> {
            override fun onComplete() {
                // no cache
                searchWithNetwork(response, page, keyword)
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }


            override fun onSuccess(searchHistory: SearchHistory) {
                // has cache
                manageCache(searchHistory, response, page, keyword)
            }
        }
    }

    private fun manageCache(
        searchHistory: SearchHistory,
        response: MutableLiveData<Resource<SearchResponse>>,
        page: Int,
        keyword: String
    ) {
        if (searchHistory.isExpired()) {
            // delete cache
            deleteCacheAndSearchWithNetwork(searchHistory, response, page, keyword)
        } else {
            // not expired
            if (!searchHistory.isFailure) {
                searchWithCache(response, page, keyword)
            } else {
                response.value = Resource.error("Cache Says: ${searchHistory.failureReason}")
            }
        }
    }

    private fun deleteCacheAndSearchWithNetwork(
        searchHistory: SearchHistory,
        response: MutableLiveData<Resource<SearchResponse>>,
        page: Int,
        keyword: String
    ) {
        Completable
            .fromAction {
                searchHistoryDao.delete(searchHistory)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchWithNetwork(response, page, keyword)
            }
    }

    private fun searchWithCache(response: MutableLiveData<Resource<SearchResponse>>, page: Int, keyword: String) {
        searchHistoryMovieRelDao.findMovies(keyword, page)
            .subscribeOn(Schedulers.io())
            .map { movies ->

                movies.map { movie ->

                    movie.title = "CACHE : ${movie.title}"
                    movie

                }

                movies
            }

            .subscribe { movies ->
                response.postValue(

                    Resource.success(
                        SearchResponse(movies, 10, null, true)
                    )
                )
            }
    }

    private fun searchWithNetwork(
        response: MutableLiveData<Resource<SearchResponse>>,
        page: Int,
        keyword: String
    ) {
        // No cache, do network here
        info("Getting from network")
        val observer = getNetworkSearchObserver(response, page, keyword)

        apiInterface.search(keyword, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
                    searchHistoryDao.insert(
                        SearchHistory(
                            null,
                            keyword,
                            page,
                            false,
                            null
                        )
                    ).subscribeOn(Schedulers.io())
                        .subscribe { searchHistoryId ->

                            println("Search history added with $searchHistoryId")

                            // Adding each movie
                            t.movies.forEach { movie ->

                                movie.createdAt = Date()

                                // checking if the movie already exist
                                movieDao.findMovieByImdbId(movie.imdbId)
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(object : MaybeObserver<SearchResponse.Movie> {
                                        override fun onSuccess(t: SearchResponse.Movie) {
                                            // has movie
                                            println("Movie already exist : ${t.title}")
                                            searchHistoryMovieRelDao.insert(
                                                SearchHistoryMovieRel(
                                                    null,
                                                    searchHistoryId,
                                                    t.id
                                                )
                                            )

                                        }

                                        override fun onComplete() {

                                            // movie not found, so adding new movie to the db
                                            movieDao.insert(movie)
                                                .subscribe(object : SingleObserver<Long> {
                                                    override fun onSuccess(movieId: Long) {

                                                        // Movie added , add the relation also
                                                        searchHistoryMovieRelDao.insert(
                                                            SearchHistoryMovieRel(
                                                                null,
                                                                searchHistoryId,
                                                                movieId
                                                            )
                                                        )

                                                    }

                                                    override fun onSubscribe(d: Disposable) {
                                                    }

                                                    override fun onError(e: Throwable) {
                                                        e.printStackTrace()
                                                    }
                                                })
                                        }

                                        override fun onSubscribe(d: Disposable) {

                                        }

                                        override fun onError(e: Throwable) {
                                            e.printStackTrace()
                                        }
                                    })

                            }

                        }


                    // success
                    response.value = Resource.success(t)


                } else {

                    val errorMessage = "${t.error} ${keyword}"

                    addSearchHistoryWithFailure(keyword, page, errorMessage)

                    // error
                    response.value = Resource.error(errorMessage)
                }
            }


            override fun onSubscribe(d: Disposable) {
                response.value = Resource.loading()
            }

            override fun onError(e: Throwable) {
                val errorReason = e.message ?: "Something went wrong"
                response.value = Resource.error(errorReason)
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
            .subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                    info("Search history with failure added")
                }

                override fun onSubscribe(d: Disposable) {
                    debug("Adding search history with failure... :(")
                }

                override fun onError(e: Throwable) {
                    error("Search history failed to add")
                }
            })
    }

}