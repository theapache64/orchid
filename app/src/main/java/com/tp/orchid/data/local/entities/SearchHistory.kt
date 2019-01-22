package com.tp.orchid.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
import java.util.concurrent.TimeUnit


@Entity(
    tableName = "search_histories",
    indices = [Index(value = ["id"])]
)
class SearchHistory(
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

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    companion object {
        // 1 day
        val SEARCH_HISTORY_AGE = TimeUnit.DAYS.toMillis(1)
    }

    fun isExpired(): Boolean {
        val millisToExpire = System.currentTimeMillis() - createdAt.time
        return millisToExpire > SEARCH_HISTORY_AGE
    }
}