package com.tp.orchid.ui.activities.main

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.data.repositories.OmdbRepository
import com.tp.orchid.utils.Resource
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(
    val omdbRepository: OmdbRepository
) : ViewModel() {

    val keyword = MutableLiveData<String>();
    val message = ObservableField<String>("Welcome!");


    private val searchResponse = Transformations.switchMap(keyword) { keyword ->
        // keyword changed
        if (keyword.isEmpty()) {
            message.set("Please enter movie name")
            return@switchMap null
        }
        omdbRepository.search(keyword, 1)
    }

    val searchMerger = MediatorLiveData<Resource<SearchResponse>>()

    init {


        searchMerger.addSource(searchResponse) {
            when (it.status) {
                Resource.Status.LOADING -> message.set("Searching '${keyword.value}'")
                Resource.Status.SUCCESS -> message.set("Found ${it.data!!.movies.size} movies")
                Resource.Status.ERROR -> message.set("${it.message}")
            }
            searchMerger.value = it

        }
    }

    fun getSearchResponse(): LiveData<Resource<SearchResponse>> = searchMerger

    override fun onCleared() {
        super.onCleared()

        omdbRepository.onCleared()
    }

    fun logout() {

    }
}