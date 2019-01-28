package com.tp.orchid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp.orchid.data.local.entities.SearchHistory
import com.tp.orchid.data.local.entities.SearchHistoryMovieRel

@Dao
interface SearchHistoryMovieRelDao {

    @Insert
    fun insert(searchHistoryMovieRel: SearchHistoryMovieRel);

    @Query("SELECT * FROM search_histories WHERE sh_keyword=:keyword AND sh_page=:page")
    fun findSearchHistory(keyword: String, page: Int): SearchHistory?


}

