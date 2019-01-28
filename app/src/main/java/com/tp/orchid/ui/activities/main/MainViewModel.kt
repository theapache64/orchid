package com.tp.orchid.ui.activities.main

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.data.repositories.OmdbRepository
import com.tp.orchid.utils.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val omdbRepository: OmdbRepository
) : ViewModel() {

    val keyword = MutableLiveData<String>("Iron Man");
    val message = ObservableField<String>("Welcome!");
    val clearListLiveData = MutableLiveData<Boolean>()

    private val searchResponse = Transformations.switchMap(keyword) { keyword ->
        // keyword changed
        if (keyword.isEmpty()) {
            message.set("Please enter movie name")
            return@switchMap null
        }

        clearListLiveData.value = true

        omdbRepository.search(keyword, 1)
    }

    val searchMerger = MediatorLiveData<Resource<List<SearchResponse.Movie>>>()

    init {

        searchMerger.addSource(searchResponse) {
            when (it.status) {
                Resource.Status.LOADING -> message.set("Searching '${keyword.value}'")
                Resource.Status.SUCCESS -> message.set("Found ${it.data?.size} movies")
                Resource.Status.ERROR -> message.set("${it.message}")
            }
            searchMerger.value = it
        }
    }

    fun getSearchResponse(): LiveData<Resource<List<SearchResponse.Movie>>> = searchMerger
    fun getClearListLiveData(): LiveData<Boolean> = clearListLiveData
}