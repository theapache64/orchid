package com.tp.theah64.orchid.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val app: Application) {

    @Provides
    fun provideContext(): Context {
        return app;
    }
}