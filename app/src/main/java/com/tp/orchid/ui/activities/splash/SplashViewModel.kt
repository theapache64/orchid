package com.tp.orchid.ui.activities.splash

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.ui.activities.login.LogInActivity
import com.tp.orchid.ui.activities.main.MainActivity
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val user: Resource<LogInResponse.User>) : ViewModel() {

    private val launchActivityEvent = SingleLiveEvent<Int>()


    fun getLaunchActivityEvent(): LiveData<Int> {
        return launchActivityEvent
    }

    fun checkUser() {
        // if user == null -> login else main
        val activityId = if (user.status == Resource.Status.ERROR) LogInActivity.ID else MainActivity.ID

        // passing id with the finish notification
        launchActivityEvent.notifyFinished(activityId)
    }


}