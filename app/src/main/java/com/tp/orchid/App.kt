package com.tp.orchid

import android.app.Activity
import android.app.Application
import com.tp.orchid.di.components.DaggerAppComponent
import com.tp.orchid.di.modules.ContextModule
import com.tp.orchid.di.modules.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    companion object {
        const val BASE_URL = "http://www.omdbapi.com/"
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .networkModule(NetworkModule(BASE_URL))
            .build()
            .inject(this)
    }
}