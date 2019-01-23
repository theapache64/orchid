package com.tp.orchid.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnotherDao {
    @Query("SELECT sh.*,shml.* FROM search_histories sh INNER JOIN search_histories_movies_rel shml ON shml.search_history_id = sh.id WHERE keyword = :keyword AND page = :page")
    fun getIt(keyword: String, page: Int): SearchHistoryWithSearchHistoryMovieRel
}