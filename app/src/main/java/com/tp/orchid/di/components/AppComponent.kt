package com.tp.orchid.di.components

import com.tp.orchid.App
import com.tp.orchid.di.modules.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            NetworkModule::class,
            UserModule::class,
            PreferenceModule::class,
            ViewModelModule::class,
            BuildersModule::class
        ]
)
interface AppComponent {
    // inject the above given modules into this App class
    fun inject(app: App)
}