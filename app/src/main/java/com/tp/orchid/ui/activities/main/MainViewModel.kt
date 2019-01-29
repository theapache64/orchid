package com.tp.orchid.ui.activities.main

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.tp.orchid.data.local.dao.SearchHistoryDao
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.data.repositories.OmdbRepository
import com.tp.orchid.utils.AppExecutors
import com.tp.orchid.utils.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val omdbRepository: OmdbRepository,
    val searchHistoryDao: SearchHistoryDao,
    val appExecutors: AppExecutors
) : ViewModel() {

    internal var isPaginationEnabled = false
    val keyword = MutableLiveData<String>();
    val page = MutableLiveData<Int>()
    val message = ObservableField<String>("Welcome!");
    val clearListLiveData = MutableLiveData<Boolean>()

    // on page number changed, load data
    private val searchResponse = Transformations.switchMap(page) { newPage ->
        return@switchMap omdbRepository.search(keyword.value!!, newPage)
    }

    init {
        // checking if the user has a search history, and if yes, pass empty, or do an initial fire of 'Iron Man'
        appExecutors.diskIO().execute {
            val isSearchedBefore = searchHistoryDao.getTotalSearchHistories() > 0
            appExecutors.mainThread().execute {
                if (!isSearchedBefore) {
                    keyword.value = "Iron Man"
                }
            }
        }
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
                Resource.Status.LOADING -> {
                    message.set("Searching '${keyword.value}'")
                    isPaginationEnabled = false
                }
                Resource.Status.SUCCESS -> {
                    val itemsPerPage = it.data?.size
                    message.set("Found ${itemsPerPage} movies")
                    isPaginationEnabled = true
                }
                Resource.Status.ERROR -> {
                    message.set("${it.message}")
                    if (it.message == "Movie not found!") {
                        isPaginationEnabled = false
                    }
                }
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