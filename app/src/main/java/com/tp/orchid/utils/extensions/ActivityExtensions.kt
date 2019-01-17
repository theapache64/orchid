package com.tp.orchid.utils.extensions

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <B : ViewDataBinding> Activity.bindContentView(@LayoutRes layoutId: Int): B {
    return DataBindingUtil.setContentView(this, layoutId)
}