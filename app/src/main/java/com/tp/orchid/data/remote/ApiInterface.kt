package com.tp.orchid.data.remote

import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.data.remote.search.SearchResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @POST("http://theapache64.com/mock_api/get_json/orchid/login")
    fun logIn(@Body logInRequest: LogInRequest): Single<LogInResponse>

    @POST("?&apiKey=75a9f74")
    fun search(@Query("s") keyword: String, @Query("page") page: Int): Single<SearchResponse>
}