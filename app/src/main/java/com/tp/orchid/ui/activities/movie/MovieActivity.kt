package com.tp.orchid.ui.activities.movie

import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.databinding.ActivityMovieBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.utils.extensions.bindContentView
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_movie.*
import javax.inject.Inject

class MovieActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // getting params
        val movie: SearchResponse.Movie = intent.getSerializableExtra(SearchResponse.Movie.KEY) as SearchResponse.Movie

        // binding
        val binding = bindContentView<ActivityMovieBinding>(R.layout.activity_movie)
        setSupportActionBar(binding.tMovie)

        // getting viewmodel
        val viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        // set params to viewmodel
        viewModel.movie = movie

        // passing viewmodel to binding
        binding.viewModel = viewModel
    }

    companion object {
        fun start(context: Context, movie: SearchResponse.Movie) {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(SearchResponse.Movie.KEY, movie)
            context.startActivity(intent)
        }
    }

}
