package com.tp.orchid.ui.activities.main

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.tp.orchid.R
import com.tp.orchid.data.remote.ApiInterface
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var apiInterface: ApiInterface

    @Inject
    lateinit var random: Random

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "$apiInterface ${random.nextInt()}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}
