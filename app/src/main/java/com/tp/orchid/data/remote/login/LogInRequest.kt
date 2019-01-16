package com.tp.orchid.data.remote.login

import com.google.gson.annotations.SerializedName

data class LogInRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)