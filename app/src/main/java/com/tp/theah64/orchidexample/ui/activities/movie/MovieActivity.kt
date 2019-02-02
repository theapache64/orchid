package com.tp.theah64.orchidexample.ui.activities.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.theah64.orchidexample.R
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import com.tp.theah64.orchidexample.databinding.ActivityMovieBinding
import com.tp.theah64.orchidexample.ui.adapters.KeyValueAdapter
import com.tp.theah64.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.theah64.orchid.utils.Resource
import com.tp.theah64.orchid.utils.extensions.bindContentView
import com.tp.theah64.orchid.utils.extensions.toast
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var adapter: KeyValueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // binding
        val binding = bindContentView<ActivityMovieBinding>(R.layout.activity_movie)
        setSupportActionBar(binding.tMovie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setTransitionName(binding.ivPoster,
            TRANSITION_NAME_POSTER
        )

        // getting viewModel
        val viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        // set params to viewModel
        viewModel.setMovie(intent.getSerializableExtra(SearchResponse.Movie.KEY) as SearchResponse.Movie)

        // building details
        val movieDetails = viewModel.getMovie().getDetailsAsKeyValues()
        this.adapter = KeyValueAdapter(this, movieDetails)

        // getting movie details
        viewModel.getMovieDetails().observe(this, Observer {

            when (it.status) {
                Resource.Status.LOADING -> {
                    /// do nothing
                }
                Resource.Status.SUCCESS -> {
                    adapter.appendKeyValues(it.data!!.getDetailsAsKeyValues())
                    viewModel.getMovieDetails().removeObservers(this)
                }
                Resource.Status.ERROR -> {
                    toast(it.message ?: "Failed to fetch more details")
                }
            }

        })


        // set params to binding
        binding.viewModel = viewModel
        binding.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_share -> {
                shareMovie()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun shareMovie() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, adapter.getShareData())
        startActivity(shareIntent)
    }

    companion object {

        const val TRANSITION_NAME_POSTER = "poster"

        fun getStartIntent(context: Context, movie: SearchResponse.Movie): Intent {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(SearchResponse.Movie.KEY, movie)
            return intent
        }
    }

}
