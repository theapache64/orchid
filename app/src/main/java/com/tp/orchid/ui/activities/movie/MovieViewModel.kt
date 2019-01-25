package com.tp.orchid.ui.activities.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.MovieDetailsDao
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.get_movie.GetMovieResponse
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.ApiResponse
import com.tp.orchid.utils.AppExecutors
import com.tp.orchid.utils.NetworkBoundResource
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
    lateinit var movie: SearchResponse.Movie

    // get more movie details
    fun getMovieDetails(imdbId: String): LiveData<Resource<GetMovieResponse>> {
        return object : NetworkBoundResource<GetMovieResponse, GetMovieResponse>(appExecutors) {
            override fun saveCallResult(item: GetMovieResponse) {
                item.movieId = movie.id
                movieDetailsDao.insert(item)
            }

            override fun shouldFetch(data: GetMovieResponse?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<GetMovieResponse> {
                return movieDetailsDao.getMovieDetails(imdbId)
            }

            override fun createCall(): LiveData<ApiResponse<GetMovieResponse>> {
                return apiInterface.getMovie(imdbId)
            }

        }.asLiveData()
    }

    fun onFavClicked() {
        appExecutors.diskIO().execute {
            movie.updatedAt = Date()
            movie.isFav = !movie.isFav
            movieDao.update(movie)
        }
    }
}