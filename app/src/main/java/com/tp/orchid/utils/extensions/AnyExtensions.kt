package com.tp.orchid.utils.extensions

import android.util.Log

fun Any.debug(message: String) {
    Log.d(this.javaClass.simpleName, message)
}

fun Any.info(message: String) {
    Log.i(this.javaClass.simpleName, message)
}

fun Any.verbose(message: String) {
    Log.v(this.javaClass.simpleName, message)
}

fun Any.error(message: String) {
    Log.e(this.javaClass.simpleName, message)
}

fun Any.warning(message: String) {
    Log.w(this.javaClass.simpleName, message)
}

fun Any.wtf(message: String) {
    Log.wtf(this.javaClass.simpleName, message)
}

