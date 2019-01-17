package com.tp.orchid.ui.activities.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.utils.extensions.snackBar
import com.tp.orchid.utils.extensions.toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val viewModel: MainViewModel = ViewModelProviders.of(this, factory)
            .get(MainViewModel::class.java)

        fab.setOnClickListener { view ->
            toast(viewModel.getGreetings())
        }
    }

}
