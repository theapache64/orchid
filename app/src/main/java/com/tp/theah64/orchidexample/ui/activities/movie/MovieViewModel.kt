package com.tp.theah64.orchidexample.ui.activities.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tp.theah64.orchidexample.data.local.dao.MovieDao
import com.tp.theah64.orchidexample.data.local.dao.MovieDetailsDao
import com.tp.theah64.orchidexample.data.remote.ApiInterface
import com.tp.theah64.orchidexample.data.remote.getmovie.GetMovieResponse
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import com.tp.theah64.orchid.utils.ApiResponse
import com.tp.theah64.orchid.utils.AppExecutors
import com.tp.theah64.orchid.utils.Resource
import com.tp.theah64.orchid.utils.cache.NetworkBoundResource
import java.util.*
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val appExecutors: AppExecutors,
    private val movieDao: MovieDao,
    private val movieDetailsDao: MovieDetailsDao,
    private val apiInterface: ApiInterface
) : ViewModel() {

    // movie
    private var movie = MutableLiveData<SearchResponse.Movie>()

    // movie details
    private val movieDetails = Transformations.switchMap(movie) { movie ->

        return@switchMap object : NetworkBoundResource<GetMovieResponse, GetMovieResponse>(appExecutors) {
            override fun saveCallResult(item: GetMovieResponse) {
                item.movieId = movie.id
                movieDetailsDao.insert(item)
            }

            override fun shouldFetch(data: GetMovieResponse?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<GetMovieResponse> {
                return movieDetailsDao.getMovieDetails(movie.imdbId)
            }

            override fun createCall(): LiveData<ApiResponse<GetMovieResponse>> {
                return apiInterface.getMovie(movie.imdbId)
            }

        }.asLiveData()

    }

    // get more movie details
    fun getMovieDetails(): LiveData<Resource<GetMovieResponse>> = movieDetails

    fun setMovie(movie: SearchResponse.Movie) {
        this.movie.value = movie
    }

    fun getMovie(): SearchResponse.Movie {
        return this.movie.value!!
    }


    fun onFavClicked() {

        val updatedMovie = movie.value!!
        updatedMovie.updatedAt = Date()
        updatedMovie.isFav = !updatedMovie.isFav

        appExecutors.diskIO().execute {
            movieDao.update(updatedMovie)
        }
    }
}