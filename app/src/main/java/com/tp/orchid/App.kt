package com.tp.orchid

import android.app.Activity
import android.app.Application
import com.tp.orchid.di.components.DaggerAppComponent
import com.tp.orchid.di.modules.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    companion object {
        const val BASE_URL = "http://theapache64.com/mock_api/get_json/orchid/"
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .networkModule(NetworkModule(BASE_URL))
            .build()
            .inject(this)
    }
}