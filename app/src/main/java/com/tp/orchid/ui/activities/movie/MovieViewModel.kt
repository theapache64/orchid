package com.tp.orchid.ui.activities.movie

import androidx.lifecycle.ViewModel
import com.tp.orchid.data.remote.search.SearchResponse
import javax.inject.Inject

class MovieViewModel @Inject constructor() : ViewModel() {
    lateinit var movie: SearchResponse.Movie
}