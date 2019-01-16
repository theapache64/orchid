package com.tp.orchid.data.remote.base

import com.google.gson.annotations.SerializedName

open class BaseApiResponse<T>(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T
)