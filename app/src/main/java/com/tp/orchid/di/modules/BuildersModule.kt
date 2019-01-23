package com.tp.orchid.di.modules

import com.tp.orchid.ui.activities.login.LogInActivity
import com.tp.orchid.ui.activities.main.MainActivity
import com.tp.orchid.ui.activities.movie.MovieActivity
import com.tp.orchid.ui.activities.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    
    @ContributesAndroidInjector()
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun bindLogInActivity(): LogInActivity

    @ContributesAndroidInjector
    abstract fun bindMovieActivity(): MovieActivity

}