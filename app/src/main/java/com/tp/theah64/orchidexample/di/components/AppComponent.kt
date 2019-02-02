package com.tp.theah64.orchidexample.di.components

import com.tp.theah64.orchidexample.App
import com.tp.theah64.orchidexample.di.modules.AppModule
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