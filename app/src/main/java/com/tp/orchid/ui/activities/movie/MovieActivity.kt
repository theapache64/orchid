package com.tp.orchid.ui.activities.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.data.local.dao.MovieDetailsDao
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.get_movie.GetMovieResponse
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.databinding.ActivityMovieBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.utils.ApiResponse
import com.tp.orchid.utils.AppExecutors
import com.tp.orchid.utils.NetworkBoundResource
import com.tp.orchid.utils.extensions.bindContentView
import com.tp.orchid.utils.extensions.info
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var apiInterface: ApiInterface

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var movieDetailsDao: MovieDetailsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // getting params
        val movie: SearchResponse.Movie = intent.getSerializableExtra(SearchResponse.Movie.KEY) as SearchResponse.Movie

        println("MainActivity : Movie id is ${movie.id}")

        // binding
        val binding = bindContentView<ActivityMovieBinding>(R.layout.activity_movie)
        setSupportActionBar(binding.tMovie)

        // getting viewmodel
        val viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        // set params to viewmodel
        viewModel.movie = movie

        // passing viewmodel to binding
        binding.viewModel = viewModel

        val liveData = object : NetworkBoundResource<GetMovieResponse, GetMovieResponse>(appExecutors) {
            override fun saveCallResult(item: GetMovieResponse) {
                println("MainActivity: Saving call result")
                item.movieId = movie.id
                movieDetailsDao.insert(item)
                println("MainActivity: Call result saved")
            }

            override fun shouldFetch(data: GetMovieResponse?): Boolean {
                info("Should fetch is ${data == null}")
                return data == null
            }

            override fun loadFromDb(): LiveData<GetMovieResponse> {
                val movieDetails = movieDetailsDao.getMovieDetails(movie.id)
                println("MainActivity: Loaded db data is $movieDetails")
                return movieDetails
            }

            override fun createCall(): LiveData<ApiResponse<GetMovieResponse>> {
                println("MainActivity: Call created")
                return apiInterface.getMovie(movie.imdbId)
            }

        }.asLiveData()

        liveData.observe(this, Observer { data ->
            println("MainActivity: Data is ${data.data}")
        })

    }

    companion object {
        fun start(context: Context, movie: SearchResponse.Movie) {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(SearchResponse.Movie.KEY, movie)
            context.startActivity(intent)
        }
    }

}
