package com.tp.orchid.di.modules

import com.tp.orchid.ui.activities.main.MainActivity
import com.tp.orchid.ui.activities.main.MainModule
import com.tp.orchid.ui.activities.splash.SplashActivity
import com.tp.orchid.ui.activities.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindSplashActivity(): SplashActivity

}