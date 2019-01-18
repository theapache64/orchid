package com.tp.orchid.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.Resource
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OmdbRepository @Inject constructor(val apiInterface: ApiInterface) {

    fun search(keyword: String, page: Int): LiveData<Resource<SearchResponse>> {
        val response = MutableLiveData<Resource<SearchResponse>>()
        apiInterface.search(keyword, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SearchResponse> {
                override fun onSuccess(t: SearchResponse) {
                    if (t.response) {
                        // success
                        response.value = Resource.success(t)
                    } else {
                        // error
                        response.value = Resource.error(t.error)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    response.value = Resource.loading()
                }

                override fun onError(e: Throwable) {
                    response.value = Resource.error(e.message ?: "Something went wrong")
                }

            })
        return response
    }
}