package com.tp.orchid.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
import java.util.concurrent.TimeUnit


@Entity(
    tableName = "search_histories",
    indices = [Index(value = ["sh_id"])]
)
class SearchHistory(
    @ColumnInfo(name = "sh_keyword")
    val keyword: String,

    @ColumnInfo(name = "sh_page")
    val page: Int,

    @ColumnInfo(name = "sh_is_failure")
    val isFailure: Boolean,

    @ColumnInfo(name = "sh_failure_reason")
    val failureReason: String?,

    @ColumnInfo(name = "sh_created_at")
    val createdAt: Date = Date()
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sh_id")
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