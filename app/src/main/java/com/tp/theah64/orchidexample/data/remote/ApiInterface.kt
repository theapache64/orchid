package com.tp.theah64.orchidexample.data.remote

import androidx.lifecycle.LiveData
import com.tp.theah64.orchidexample.data.remote.getmovie.GetMovieResponse
import com.tp.theah64.orchidexample.data.remote.login.LogInRequest
import com.tp.theah64.orchidexample.data.remote.login.LogInResponse
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import com.tp.theah64.orchid.utils.ApiResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("http://theapache64.com/mock_api/get_json/orchid/login")
    fun logIn(@Body logInRequest: LogInRequest): Single<LogInResponse>

    @GET("?&apiKey=75a9f74")
    fun search(@Query("s") keyword: String, @Query("page") page: Int): LiveData<ApiResponse<SearchResponse>>

    @GET("?&apiKey=75a9f74&plot=full")
    fun getMovie(@Query("i") imdbId: String): LiveData<ApiResponse<GetMovieResponse>>
}