package com.tp.orchid.ui.activities.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
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