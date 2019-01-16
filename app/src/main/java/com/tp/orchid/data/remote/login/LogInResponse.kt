package com.tp.orchid.data.remote.login

import com.google.gson.annotations.SerializedName
import com.tp.orchid.data.remote.base.BaseApiResponse


/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Wed Jan 16 14:49:46 UTC 2019
 */
class LogInResponse(error: Boolean, message: String, data: Data) :
    BaseApiResponse<LogInResponse.Data>(error, message, data) {

    class Data(
        @SerializedName("user") val user: User
    )

    class User(
        @SerializedName("name") val name: String,
        @SerializedName("api_key") val apiKey: String
    )
}