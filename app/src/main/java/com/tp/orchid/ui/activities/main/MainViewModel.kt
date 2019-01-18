package com.tp.orchid.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.data.repositories.OmdbRepository
import com.tp.orchid.utils.Resource
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(omdbRepository: OmdbRepository) : ViewModel() {
    val keyword = MutableLiveData<String>();
    private val searchResponse = Transformations.switchMap(keyword) {
        omdbRepository.search(it, 1)
    }

    fun getSearchResponse(): LiveData<Resource<SearchResponse>> = searchResponse
}