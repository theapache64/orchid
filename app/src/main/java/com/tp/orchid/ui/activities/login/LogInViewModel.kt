package com.tp.orchid.ui.activities.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.*
import com.google.gson.Gson
import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.data.repositories.UserRepository
import com.tp.orchid.utils.Resource
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private val loginRequestLiveData = MutableLiveData<LogInRequest>()
    private val loginResponseLiveData = Transformations.switchMap(loginRequestLiveData) {
        userRepository.login(it)
    }

    private val loginMerger = MediatorLiveData<Resource<LogInResponse>>()

    init {
        loginMerger.addSource(loginResponseLiveData) {
            if (it.status == Resource.Status.SUCCESS) {
                saveUser(it.data!!.data.user)
            }
            loginMerger.value = it
        }
    }

    /**
     * Saves user as json string
     */
    private fun saveUser(user: LogInResponse.User) {
        sharedPreferences.edit {
            putString(LogInResponse.User.KEY, gson.toJson(user))
        }
    }

    /**
     * Login button pressed
     */
    fun onLogInPressed() {

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            loginMerger.value = Resource.error("Invalid username and password", null)
            return
        }


        loginRequestLiveData.value = LogInRequest(username, password)
    }

    fun getLogInLiveData(): LiveData<Resource<LogInResponse>> = loginMerger


}