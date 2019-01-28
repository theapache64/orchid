package com.tp.orchid.data.remote

import androidx.lifecycle.LiveData
import com.tp.orchid.data.remote.getmovie.GetMovieResponse
import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.ApiResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @POST("http://theapache64.com/mock_api/get_json/orchid/login")
    fun logIn(@Body logInRequest: LogInRequest): Single<LogInResponse>

    @GET("?&apiKey=75a9f74")
    fun search(@Query("s") keyword: String, @Query("page") page: Int): LiveData<ApiResponse<SearchResponse>>

    @GET("?&apiKey=75a9f74&plot=full")
    fun getMovie(@Query("i") imdbId: String): LiveData<ApiResponse<GetMovieResponse>>
}