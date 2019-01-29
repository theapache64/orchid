package com.tp.orchid.ui.activities.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp.orchid.R
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.databinding.ActivityMainBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.ui.activities.favorites.FavoritesActivity
import com.tp.orchid.ui.activities.login.LogInActivity
import com.tp.orchid.ui.adapters.MoviesAdapter
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.bindContentView
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
        val adapter = MoviesAdapter(this, MovieCallbackImpl(this))

        binding.adapter = adapter


        // Watching for movie response
        viewModel.getSearchResponse().observe(this, Observer { response ->

            when (response.status) {

                Resource.Status.LOADING -> {
                    binding.include.progressMovies.show()
                }

                Resource.Status.ERROR -> {
                    binding.include.progressMovies.hide()
                }

                Resource.Status.SUCCESS -> {

                    binding.include.progressMovies.hide()

                    if (response.data != null && response.data.isNotEmpty()) {
                        adapter.appendMovies(response.data)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })

        // Watching for clear list command
        viewModel.getClearListLiveData().observe(this, Observer { isClear ->
            if (isClear) {
                adapter.clearMovies()
                adapter.notifyDataSetChanged()
            }
        })

        // pagination using recyclerview
        binding.include.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    val mLayoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = mLayoutManager.getChildCount()
                    val totalItemCount = mLayoutManager.getItemCount()
                    val pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (viewModel.isPaginationEnabled) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            Log.v("...", "Last Item Wow !")
                            viewModel.loadNextPage()
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
