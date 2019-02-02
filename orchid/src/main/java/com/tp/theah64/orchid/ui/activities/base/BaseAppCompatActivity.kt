package com.tp.theah64.orchid.ui.activities.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseAppCompatActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }
}