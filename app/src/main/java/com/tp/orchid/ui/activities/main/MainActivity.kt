package com.tp.orchid.ui.activities.main

import android.os.Bundle
import com.tp.orchid.R
import com.tp.orchid.utils.extensions.snackBar
import com.tp.orchid.utils.extensions.toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            toast("Hello")
        }
    }

}
