package com.tp.theah64.orchidexample.ui.activities.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.theah64.orchidexample.R
import com.tp.theah64.orchidexample.ui.activities.login.LogInActivity
import com.tp.theah64.orchidexample.ui.activities.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)

        // Watching activity launch command
        viewModel.getLaunchActivityEvent().observe(this, Observer { activityId ->

            when (activityId) {
                MainActivity.ID -> {
                    startActivity(MainActivity.getStartIntent(this))
                }
                LogInActivity.ID -> {
                    startActivity(LogInActivity.getStartIntent(this))
                }
                else -> throw IllegalArgumentException("Undefined activity id $activityId")
            }

            finish()

        })

        // Starting splash timer
        Handler().postDelayed({
            viewModel.checkUser();
        }, SPLASH_DURATION)

    }


    companion object {
        private const val SPLASH_DURATION = 1000L
    }
}

