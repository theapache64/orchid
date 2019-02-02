package com.tp.theah64.orchidexample.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tp.theah64.orchidexample.data.local.entities.SearchHistory

@Dao
interface SearchHistoryDao {

    @Insert
    fun insert(searchHistory: SearchHistory): Long

    @Delete
    fun delete(searchHistory: SearchHistory)

    @Query("SELECT COUNT(sh_id) AS total_hits FROM search_histories")
    fun getTotalSearchHistories(): Long
}