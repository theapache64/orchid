package com.tp.orchid.di.components

import com.tp.orchid.App
import com.tp.orchid.di.modules.AppModule
import com.tp.orchid.di.modules.BuildersModule
import com.tp.orchid.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        BuildersModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)
}