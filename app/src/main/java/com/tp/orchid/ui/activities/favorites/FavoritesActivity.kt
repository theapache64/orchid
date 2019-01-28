package com.tp.orchid.ui.activities.favorites

import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.databinding.ActivityFavoritesBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.ui.activities.main.MovieCallbackImpl
import com.tp.orchid.ui.adapters.MoviesAdapter
import com.tp.orchid.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class FavoritesActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityFavoritesBinding>(R.layout.activity_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel: FavoritesViewModel = ViewModelProviders.of(this, factory)
            .get(FavoritesViewModel::class.java)

        val moviesAdapter = MoviesAdapter(this, MovieCallbackImpl(this))

        viewModel.getFavorites().observe(this, Observer {
            moviesAdapter.appendMovies(it)
            moviesAdapter.notifyDataSetChanged()
        })

        binding.adapter = moviesAdapter

    }


    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, FavoritesActivity::class.java)
    }
}
