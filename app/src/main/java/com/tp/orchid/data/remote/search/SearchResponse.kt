package com.tp.orchid.data.remote.search


import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.tp.orchid.data.remote.base.BaseOmdbApiResponse
import java.util.*

/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Fri Jan 18 10:57:12 UTC 2019
 */
class SearchResponse(
    @SerializedName("Search") val movies: List<Movie>,
    @SerializedName("totalResults") val totalResults: Int,
    error: String?, response: Boolean
) : BaseOmdbApiResponse(error, response) {

    @Entity(
        tableName = "movies",
        indices = [
            Index("imdb_id", unique = true)
        ]
    )
    class Movie(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long,

        @ColumnInfo(name = "type")
        @SerializedName("Type")
        val type: String,

        @ColumnInfo(name = "year")
        @SerializedName("Year")
        val year: String,

        @ColumnInfo(name = "title")
        @SerializedName("Title")
        var title: String,

        @ColumnInfo(name = "imdb_id")
        @SerializedName("imdbID")
        val imdbId: String,

        @ColumnInfo(name = "poster_url")
        @SerializedName("Poster")
        val poster: String,

        @ColumnInfo(name = "created_at")
        var createdAt: Date = Date()
    )
}