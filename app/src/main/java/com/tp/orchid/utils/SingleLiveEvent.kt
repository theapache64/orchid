package com.tp.orchid.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Normally LiveData can be fired at many times, but this special live data wrapper only fires once.
 */
class SingleLiveEvent<T> : LiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            observer.onChanged(it)
            removeObservers(owner)
        })
    }

    fun done(value: T) {
        this.value = value
    }
}
