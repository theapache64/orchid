package com.tp.orchid.data.remote.search


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.your.packagename.api.responses.BaseOmdbApiResponse

/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Fri Jan 18 10:57:12 UTC 2019
 */
class SearchResponse(
    @SerializedName("Search") val movies: List<Movie>,
    @SerializedName("totalResults") val totalResults: Int,
    error: String, response: Boolean
) : BaseOmdbApiResponse(error, response) {

    @Entity(tableName = "movies")
    class Movie(

        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @ColumnInfo(name = "type")
        @SerializedName("Type")
        val type: String,

        @ColumnInfo(name = "year")
        @SerializedName("Year")
        val year: String,

        @ColumnInfo(name = "title")
        @SerializedName("Title")
        val title: String,

        @ColumnInfo(name = "imdb_id")
        @SerializedName("imdbID")
        val imdbId: String,

        @ColumnInfo(name = "poster_url")
        @SerializedName("Poster")
        val poster: String
    )
}