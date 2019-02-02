package com.tp.theah64.orchidexample.di.modules

import com.tp.theah64.orchidexample.ui.activities.favorites.FavoritesActivity
import com.tp.theah64.orchidexample.ui.activities.login.LogInActivity
import com.tp.theah64.orchidexample.ui.activities.main.MainActivity
import com.tp.theah64.orchidexample.ui.activities.movie.MovieActivity
import com.tp.theah64.orchidexample.ui.activities.splash.SplashActivity
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

    @ContributesAndroidInjector
    abstract fun bindFavoritesActivity(): FavoritesActivity
}