package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel
import com.tp.orchid.data.remote.search.SearchResponse

@Dao
interface SearchHistoryMovieRelDao {

    @Insert
    fun insert(searchHistoryMovieRel: SearchHistoryMovieRel);

    @Query("SELECT * FROM search_histories WHERE sh_keyword=:keyword AND sh_page=:page")
    fun findSearchHistory(keyword: String, page: Int): SearchHistory?


}

