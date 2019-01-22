package com.tp.orchid.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tp.orchid.data.local.entities.SearchHistory
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface SearchHistoryDao {

    @Insert
    fun insert(searchHistory: SearchHistory): Single<Long>

    @Delete
    fun delete(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_histories WHERE keyword=:keyword AND page=:page")
    fun findSearchHistory(keyword: String, page: Int): Maybe<SearchHistory>
}