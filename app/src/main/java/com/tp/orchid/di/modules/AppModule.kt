package com.tp.orchid.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetworkModule::class,
        UserModule::class,
        PreferenceModule::class,
        ViewModelModule::class,
        BuildersModule::class,
        DatabaseModule::class
    ]
)
class AppModule(val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return this.application
    }

}