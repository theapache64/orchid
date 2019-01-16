package com.tp.orchid.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * To show short + simple snackbar with just a message
 */
fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}