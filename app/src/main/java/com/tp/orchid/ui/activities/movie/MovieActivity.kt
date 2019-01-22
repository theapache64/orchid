package com.tp.orchid.ui.activities.movie

import android.os.Bundle
import android.app.Activity
import com.tp.orchid.R
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity

import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
    }

}
