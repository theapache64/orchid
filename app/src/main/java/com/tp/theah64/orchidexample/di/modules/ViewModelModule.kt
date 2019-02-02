package com.tp.theah64.orchidexample.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tp.theah64.orchidexample.ui.activities.favorites.FavoritesViewModel
import com.tp.theah64.orchidexample.ui.activities.login.LogInViewModel
import com.tp.theah64.orchidexample.ui.activities.main.MainViewModel
import com.tp.theah64.orchidexample.ui.activities.movie.MovieViewModel
import com.tp.theah64.orchidexample.ui.activities.splash.SplashViewModel
import com.tp.theah64.orchid.utils.viewmodel.ViewModelFactory
import com.tp.theah64.orchid.utils.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    abstract fun bindLogInViewModel(viewModel: LogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel
}