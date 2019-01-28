package com.tp.orchid.data.local.dao

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
    fun insert(searchHistory: SearchHistory): Long

    @Delete
    fun delete(searchHistory: SearchHistory)
}