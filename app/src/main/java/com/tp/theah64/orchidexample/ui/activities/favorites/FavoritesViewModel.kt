package com.tp.theah64.orchidexample.ui.activities.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tp.theah64.orchidexample.data.local.dao.MovieDao
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val movieDao: MovieDao
) : ViewModel() {

    fun getFavorites(): LiveData<List<SearchResponse.Movie>> {
        return movieDao.getFavMovies()
    }

}