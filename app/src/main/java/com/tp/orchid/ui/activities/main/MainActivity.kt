package com.tp.orchid.ui.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.utils.extensions.snackBar
import com.tp.orchid.utils.extensions.toast
import com.tp.orchid.utils.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    companion object {

        const val ID = R.id.MAIN_ACTIVITY_ID

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val viewModel: MainViewModel = ViewModelProviders.of(this, factory)
            .get(MainViewModel::class.java)

        fab.setOnClickListener { view ->

        }
    }

}
