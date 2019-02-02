package com.tp.theah64.orchidexample.ui.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tp.theah64.orchidexample.data.remote.login.LogInResponse
import com.tp.theah64.orchidexample.ui.activities.login.LogInActivity
import com.tp.theah64.orchidexample.ui.activities.main.MainActivity
import com.tp.theah64.orchid.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val user: LogInResponse.User?) : ViewModel() {

    private val launchActivityEvent = SingleLiveEvent<Int>()


    fun getLaunchActivityEvent(): LiveData<Int> {
        return launchActivityEvent
    }

    fun checkUser() {
        // if user == null -> login else main
        val activityId = if (user == null) LogInActivity.ID else MainActivity.ID

        // passing id with the finish notification
        launchActivityEvent.notifyFinished(activityId)
    }


}