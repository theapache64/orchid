package com.tp.orchid.di.components

import android.app.Application
import android.os.Build
import com.tp.orchid.App
import com.tp.orchid.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    // inject the above given modules into this App class
    fun inject(app: App)
}