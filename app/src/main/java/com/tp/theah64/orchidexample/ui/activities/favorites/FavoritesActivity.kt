package com.tp.theah64.orchidexample.ui.activities.favorites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.theah64.orchidexample.R
import com.tp.theah64.orchidexample.databinding.ActivityFavoritesBinding
import com.tp.theah64.orchidexample.ui.activities.main.MovieCallbackImpl
import com.tp.theah64.orchidexample.ui.adapters.MoviesAdapter
import com.tp.theah64.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.theah64.orchid.utils.extensions.bindContentView
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

        val moviesAdapter = MoviesAdapter(
            this,
            MovieCallbackImpl(this)
        )

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
