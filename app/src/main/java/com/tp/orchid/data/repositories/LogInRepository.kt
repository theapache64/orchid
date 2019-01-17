package com.tp.orchid.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.utils.Resource
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LogInRepository @Inject constructor(val apiInterface: ApiInterface) {

    @SuppressLint("CheckResult")
    fun login(logInRequest: LogInRequest): LiveData<Resource<LogInResponse>> {

        val logInLiveData = MutableLiveData<Resource<LogInResponse>>()

        apiInterface.logIn(logInRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : SingleObserver<LogInResponse> {
                    override fun onSuccess(t: LogInResponse) {
                        logInLiveData.value = Resource.success(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        logInLiveData.value = Resource.loading(null)
                    }

                    override fun onError(e: Throwable) {
                        logInLiveData.value = Resource.error(e.message!!, null)
                    }
                })

        return logInLiveData;
    }
}