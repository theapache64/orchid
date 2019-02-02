package com.tp.theah64.orchidexample.di.modules

import android.app.Application
import com.tp.theah64.orchid.di.modules.PreferenceModule
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