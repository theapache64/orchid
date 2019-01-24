package com.tp.orchid.models

import androidx.annotation.StringRes

class KeyValue(
    @StringRes
    val key: Int,
    _value: String?
) {
    var value: String = _value ?: "N/A"
        set(newValue) {
            field = newValue.capitalize()
        }
}
