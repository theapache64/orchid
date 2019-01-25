package com.tp.orchid.ui.activities.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.local.dao.MovieDao
import com.tp.orchid.data.local.dao.SearchHistoryMovieRelDao
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.utils.AppExecutors
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val movieDao: MovieDao,
    appExecutors: AppExecutors
) : ViewModel() {
    private val favoritesLiveData: LiveData<List<SearchResponse.Movie>> = movieDao.getFavMovies()

    fun getFavorites(): LiveData<List<SearchResponse.Movie>> {
        return favoritesLiveData
    }

}