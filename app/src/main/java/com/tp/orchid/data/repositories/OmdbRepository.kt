package com.tp.orchid.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.debug
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
class OmdbRepository @Inject constructor(val apiInterface: ApiInterface, val movieDao: MovieDao) {

    fun search(keyword: String, page: Int): LiveData<Resource<SearchResponse>> {

        val response = MutableLiveData<Resource<SearchResponse>>()

        val observer = object : SingleObserver<SearchResponse> {
            override fun onSuccess(t: SearchResponse) {
                if (t.response) {

                    addToDb(t.movies)

                    // success
                    response.value = Resource.success(t)
                } else {
                    // error
                    response.value = Resource.error("${t.error} ${keyword}")
                }
            }

            @SuppressLint("CheckResult")


            override fun onSubscribe(d: Disposable) {
                response.value = Resource.loading()
            }

            override fun onError(e: Throwable) {
                response.value = Resource.error(e.message ?: "Something went wrong")
            }

        }

        apiInterface.search(keyword, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

        return response
    }

    private fun addToDb(movies: List<SearchResponse.Movie>) {
        Completable
            .fromAction {
                movieDao.insertAll(movies)
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}