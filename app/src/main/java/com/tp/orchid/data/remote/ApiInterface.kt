package com.tp.orchid.data.remote

import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("login")
    fun logIn(@Body logInRequest: LogInRequest): Single<LogInResponse>
}