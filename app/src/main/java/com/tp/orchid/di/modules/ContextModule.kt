package com.tp.orchid.di.modules

import android.app.Application
import android.content.Context
import com.tp.orchid.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(val app: Application) {

    @Provides
    fun provideContext(): Context {
        return app;
    }
}