package com.tp.orchid.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.SearchHistoryDao
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.debug
import com.tp.orchid.utils.extensions.error
import com.tp.orchid.utils.extensions.info
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OmdbRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val movieDao: MovieDao,
    val searchHistoryDao: SearchHistoryDao
) {

    /**
     * Search for movies with given keyword
     */
    fun search(keyword: String, page: Int): LiveData<Resource<SearchResponse>> {

        val response = MutableLiveData<Resource<SearchResponse>>()

        if (hasValidCache(keyword, page)) {
            // get from cache
            info("Getting from cache")

        } else {

            // get from network
            info("Getting from network")
            val observer = getSearchObserver(response, page, keyword)

            apiInterface.search(keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        }

        return response
    }

    private fun hasValidCache(keyword: String, page: Int): Boolean {
        val searchHistory = searchHistoryDao.findSearchHistory(keyword, page)
        return false;
    }

    private fun isCacheExpired(): Boolean {
        return true
    }

    private fun hasCache(keyword: String, page: Int): Boolean {
        return false
    }

    /**
     * Returnx RxJava network observer
     */
    private fun getSearchObserver(
        response: MutableLiveData<Resource<SearchResponse>>,
        page: Int,
        keyword: String
    ): SingleObserver<SearchResponse> {
        return object : SingleObserver<SearchResponse> {
            override fun onSuccess(t: SearchResponse) {
                if (t.response) {

//                    addSearchHistoryWithMovies(keyword, page, t.movies)

                    // success
                    response.value = Resource.success(t)
                } else {

                    val errorMessage = "${t.error} ${keyword}"

                    addSearchHistoryWithFailure(keyword, page, errorMessage)

                    // error
                    response.value = Resource.error(errorMessage)
                }
            }

            @SuppressLint("CheckResult")


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


    private fun addSearchHistoryWithMovies(keyword: String, page: Int, movies: List<SearchResponse.Movie>) {
        Completable
            .fromAction {
                movieDao.insertAll(movies)
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}