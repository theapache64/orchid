package com.tp.orchid.di.modules

import android.app.Application
import android.content.Context
import com.tp.orchid.di.scopes.AppScope
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ContextModule(val app: Application) {

    @AppScope
    fun provideContext(): Context {
        return app;
    }
}