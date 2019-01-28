package com.tp.orchid.ui.activities.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.MovieDetailsDao
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.getmovie.GetMovieResponse
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.ApiResponse
import com.tp.orchid.utils.AppExecutors
import com.tp.orchid.utils.cache.NetworkBoundResource
import com.tp.orchid.utils.Resource
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