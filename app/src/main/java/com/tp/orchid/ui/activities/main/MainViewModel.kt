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

    val totalPages: Int = 0
    val keyword = MutableLiveData<String>("God Father");
    val page = MutableLiveData<Int>()
    val message = ObservableField<String>("Welcome!");
    val clearListLiveData = MutableLiveData<Boolean>()

    // on page number changed, load data
    private val searchResponse = Transformations.switchMap(page) { newPage ->
        return@switchMap omdbRepository.search(keyword.value!!, newPage)
    }

    private val searchMerger = MediatorLiveData<Resource<List<SearchResponse.Movie>>>()

    init {

        // keyword changed, change page no to 1
        searchMerger.addSource(keyword) { keyword ->

            if (keyword.isEmpty()) {
                message.set("Please enter movie name")
                return@addSource
            }

            clearListLiveData.value = true

            // change page no
            page.value = 1
        }

        // searchResponse changed
        searchMerger.addSource(searchResponse) {
            when (it.status) {
                Resource.Status.LOADING -> message.set("Searching '${keyword.value}'")
                Resource.Status.SUCCESS -> {
                    val itemsPerPage = it.data?.size
                    message.set("Found ${itemsPerPage} movies")
                }
                Resource.Status.ERROR -> message.set("${it.message}")
            }
            searchMerger.value = it
        }
    }

    fun getSearchResponse(): LiveData<Resource<List<SearchResponse.Movie>>> = searchMerger
    fun getClearListLiveData(): LiveData<Boolean> = clearListLiveData
    fun loadNextPage() {
        page.value = page.value!! + 1
    }
}