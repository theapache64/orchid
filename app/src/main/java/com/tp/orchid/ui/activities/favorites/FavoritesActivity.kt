package com.tp.orchid.ui.activities.favorites

import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class FavoritesActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val viewModel: FavoritesViewModel = ViewModelProviders.of(this, factory)
            .get(FavoritesViewModel::class.java)
    }

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, FavoritesActivity::class.java)
    }
}
