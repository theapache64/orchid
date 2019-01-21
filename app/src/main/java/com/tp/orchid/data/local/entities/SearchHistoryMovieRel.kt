package com.tp.orchid.data.local.entities

import androidx.room.*
import com.tp.orchid.data.remote.search.SearchResponse
import java.util.*

@Entity(
    tableName = "search_histories_movies_rel",
    indices = [Index("movie_id", "search_history_id")],
    foreignKeys = [
        // search_history_id
        ForeignKey(
            entity = SearchHistory::class,
            parentColumns = ["id"],
            childColumns = ["search_history_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),

        // movie_id
        ForeignKey(
            entity = SearchResponse.Movie::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class SearchHistoryMovieRel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,

    @ColumnInfo(name = "search_history_id")
    val searchHistoryId: Long,

    @ColumnInfo(name = "movie_id")
    val movieId: Long,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)
