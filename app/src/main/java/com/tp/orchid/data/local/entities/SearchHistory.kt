package com.tp.orchid.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.concurrent.TimeUnit


@Entity(tableName = "search_histories")
class SearchHistory(

    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    @ColumnInfo(name = "keyword")
    val keyword: String,

    @ColumnInfo(name = "page")
    val page: Int,

    @ColumnInfo(name = "is_failure")
    val isFailure: Boolean,

    @ColumnInfo(name = "failure_reason")
    val failureReason: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
) {

    companion object {
        // 1 day
        val SEARCH_HISTORY_AGE = TimeUnit.DAYS.toMillis(1)
    }

    fun isExpired(): Boolean {
        val millisToExpire = System.currentTimeMillis() - createdAt.time
        return millisToExpire > SEARCH_HISTORY_AGE
    }
}