package com.tp.orchid.ui.activities.login

import androidx.lifecycle.ViewModel
import com.tp.orchid.utils.extensions.info
import javax.inject.Inject

class LogInViewModel @Inject constructor() : ViewModel() {
    var username: String = ""
    var password: String = ""

    fun onLogInPressed() {

    }
}