package com.tp.orchid.ui.activities.splash

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.remote.login.LogInResponse
import javax.inject.Inject

class SplashViewModel @Inject constructor(val user: LogInResponse.User?) : ViewModel() {

    fun startTimer() {
        Handler().postDelayed({

        }, SPLASH_DURATION)
    }

    companion object {
        private const val SPLASH_DURATION = 1000L
    }


}