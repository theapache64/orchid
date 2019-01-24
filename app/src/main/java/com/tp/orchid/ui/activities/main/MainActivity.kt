package com.tp.orchid.ui.activities.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.databinding.ActivityMainBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.ui.activities.login.LogInActivity
import com.tp.orchid.ui.activities.movie.MovieActivity
import com.tp.orchid.ui.adapters.MoviesAdapter
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.bindContentView
import com.tp.orchid.utils.extensions.info
import com.tp.orchid.utils.extensions.error
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.core.util.Pair as AndroidPair

class MainActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory;

    @Inject
    lateinit var sharedPreferences: SharedPreferences;

    @set:Inject
    var user: LogInResponse.User? = null;


    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityMainBinding>(R.layout.activity_main)
        setSupportActionBar(toolbar)


        this.viewModel = ViewModelProviders.of(this, factory)
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        // Adapter
        val adapter = MoviesAdapter(this, object : MoviesAdapter.Callback {
            override fun onMovieClicked(view: View, movie: SearchResponse.Movie) {

                val startIntent = MovieActivity.getStartIntent(this@MainActivity, movie)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@MainActivity,
                        AndroidPair<View, String>(view.findViewById(R.id.iv_poster), "poster"),
                        AndroidPair<View, String>(view.findViewById(R.id.tv_title), "title")
                    )

                    startActivity(
                        startIntent,
                        options.toBundle()
                    )
                } else {
                    startActivity(startIntent)
                }
            }
        })

        binding.adapter = adapter

        // Watching for movie response
        viewModel.getSearchResponse().observe(this, Observer { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    adapter.clearMovies()
                }
                Resource.Status.SUCCESS -> {
                    info("Success :")
                    if (response.data != null) {
                        adapter.setMovies(response.data)
                    }
                }
                Resource.Status.ERROR -> {
                    error("Error")
                    adapter.clearMovies()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_logout -> {

                // Clearing all pref
                sharedPreferences.edit {
                    clear()
                }

                startActivity(LogInActivity.getStartIntent(this))
                finish()

            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    companion object {
        const val ID = R.id.MAIN_ACTIVITY_ID
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}
