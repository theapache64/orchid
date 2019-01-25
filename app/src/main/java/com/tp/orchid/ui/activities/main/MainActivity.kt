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
import com.tp.orchid.ui.activities.favorites.FavoritesActivity
import com.tp.orchid.ui.activities.login.LogInActivity
import com.tp.orchid.ui.activities.movie.MovieActivity
import com.tp.orchid.ui.adapters.MoviesAdapter
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.bindContentView
import com.tp.orchid.utils.extensions.error
import com.tp.orchid.utils.extensions.info
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.core.util.Pair as AndroidPair
import android.widget.Toast
import android.R
import androidx.recyclerview.widget.RecyclerView
import android.R
import androidx.recyclerview.widget.GridLayoutManager


class MainActivity : BaseAppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory;

    @Inject
    lateinit var sharedPreferences: SharedPreferences;

    @set:Inject
    var user: LogInResponse.User? = null;


    private lateinit var viewModel: MainViewModel

    private var isLoadingEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityMainBinding>(com.tp.orchid.R.layout.activity_main)
        setSupportActionBar(toolbar)


        this.viewModel = ViewModelProviders.of(this, factory)
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        // Adapter
        val adapter = MoviesAdapter(this, MovieCallbackImpl(this))

        binding.adapter = adapter

        // Watching for movie response
        viewModel.getSearchResponse().observe(this, Observer { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    adapter.clearMovies()
                    adapter.notifyDataSetChanged()
                }
                Resource.Status.SUCCESS -> {
                    info("Success :")
                    if (response.data != null && response.data.isNotEmpty()) {
                        adapter.setMovies(response.data)
                        adapter.notifyDataSetChanged()
                    }
                }
                Resource.Status.ERROR -> {
                    error("Error")
                    adapter.clearMovies()
                    adapter.notifyDataSetChanged()
                }
            }
        })



        binding.include.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {

                    val mLayoutManager = recyclerView.layoutManager!! as GridLayoutManager

                    // Checking for scroll down
                    val visibleItemCount = mLayoutManager.getChildCount()
                    val totalItemCount = mLayoutManager.getItemCount()
                    val pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (isLoadingEnabled) {

                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            isLoadingEnabled = false

                            Log.v("...", "Last Item Wow !")
                            //Do pagination.. i.e. fetch new data

                            if (hasMoreNotifications) {
                                page += 1
                                SingletonToast.makeText(
                                    getActivity(),
                                    R.string.Loading_more_notifications,
                                    Toast.LENGTH_LONG
                                ).show()
                                presenter.loadNotifications(token, page)
                            }

                        }
                    }
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

            R.id.action_fav -> {
                startActivity(FavoritesActivity.getStartIntent(this))
            }

            R.id.action_logout -> {
                logout()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun logout() {
        // Clearing all pref
        sharedPreferences.edit {
            clear()
        }

        startActivity(LogInActivity.getStartIntent(this))
        finish()
    }


    companion object {
        const val ID = R.id.MAIN_ACTIVITY_ID
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}
