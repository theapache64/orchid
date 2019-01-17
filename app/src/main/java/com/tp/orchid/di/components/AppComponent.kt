package com.tp.orchid.di.components

import com.tp.orchid.App
import com.tp.orchid.di.modules.AppModule
import com.tp.orchid.di.modules.BuildersModule
import com.tp.orchid.di.modules.NetworkModule
import com.tp.orchid.di.modules.ViewModelModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        BuildersModule::class
    ]
)
interface AppComponent {
    // inject the above given modules into this App class
    fun inject(app: App)
}