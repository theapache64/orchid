package com.tp.orchid.ui.activities.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.*
import com.google.gson.Gson
import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.data.repositories.LogInRepository
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.info
import javax.inject.Inject

class LogInViewModel @Inject constructor(
        val logInRepository: LogInRepository,
        val sharedPreferences: SharedPreferences,
        val gson: Gson
) : ViewModel() {

    var username: String = ""

    var password: String = ""
    private val loginRequestLiveData = MutableLiveData<LogInRequest>()
    private val loginResponseLiveData = Transformations.switchMap(loginRequestLiveData) {
        logInRepository.login(it)
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

    private fun saveUser(user: LogInResponse.User) {
        sharedPreferences.edit {
            putString(LogInResponse.User.KEY, gson.toJson(user))
            commit()
        }
    }

    fun onLogInPressed() {
        loginRequestLiveData.value = LogInRequest(username, password)
    }

    fun getLogInLiveData(): LiveData<Resource<LogInResponse>> = loginMerger


}