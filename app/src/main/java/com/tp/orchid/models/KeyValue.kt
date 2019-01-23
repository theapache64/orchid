package com.tp.orchid.models

import androidx.annotation.StringRes

data class KeyValue(
    @StringRes
    val key: Int,

    val value: String
)