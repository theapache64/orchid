package com.tp.orchid.ui.activities.splash

import androidx.lifecycle.ViewModel
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.login.LogInResponse
import javax.inject.Inject

class SplashViewModel @Inject constructor(val user: ApiInterface) : ViewModel() {
    fun startTimer() {
        println("User is $user")
    }

}