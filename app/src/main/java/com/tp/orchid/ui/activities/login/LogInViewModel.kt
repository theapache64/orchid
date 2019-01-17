package com.tp.orchid.ui.activities.login

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.data.repositories.LogInRepository
import com.tp.orchid.utils.Resource
import javax.inject.Inject

class LogInViewModel @Inject constructor(val logInRepository: LogInRepository) : ViewModel() {

    var username: String = ""

    var password: String = ""
    private val loginRequestLiveData = MutableLiveData<LogInRequest>()
    private val loginResponseLiveData = Transformations.switchMap(loginRequestLiveData) {
        logInRepository.login(it)
    }

    fun onLogInPressed() {
        loginRequestLiveData.value = LogInRequest(username, password)
    }

    fun getLogInLiveData(): LiveData<Resource<LogInResponse>> = loginResponseLiveData


}