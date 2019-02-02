package com.tp.theah64.orchidexample.data.local.entities

import androidx.room.*
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import java.util.*

@Entity(
    tableName = "search_histories_movies_rel",
    indices = [Index(value = ["shmr_movie_id", "shmr_search_history_id"], unique = true)],
    foreignKeys = [

        // search_history_id
        ForeignKey(
            entity = SearchHistory::class,
            parentColumns = ["sh_id"],
            childColumns = ["shmr_search_history_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),

        // movie_id
        ForeignKey(
            entity = SearchResponse.Movie::class,
            parentColumns = ["mv_id"],
            childColumns = ["shmr_movie_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SearchHistoryMovieRel(
    @ColumnInfo(name = "shmr_search_history_id")
    val searchHistoryId: Long,

    @ColumnInfo(name = "shmr_movie_id")
    val movieId: Long,

    @ColumnInfo(name = "shmr_created_at")
    val createdAt: Date = Date()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shmr_id")
    var id: Long = 0
}
