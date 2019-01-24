package com.tp.orchid.data.remote.base

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName


/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Fri Jan 18 10:59:13 UTC 2019
 */
open class BaseOmdbApiResponse(
    @SerializedName("Error") val error: String?,
    @SerializedName("Response") val response: Boolean
)