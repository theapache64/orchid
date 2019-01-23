package com.tp.orchid.data.remote.get_movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Tue Jan 22 07:49:00 UTC 2019
 */
@Entity(tableName = "movie_details")
data class GetMovieResponse(

    @ColumnInfo(name = "dvd")
    @SerializedName("DVD")
    val dvd: String?,

    @ColumnInfo(name = "plot")
    @SerializedName("Plot")
    val plot: String,

    @ColumnInfo(name = "rated")
    @SerializedName("Rated")
    val rated: String,

    @ColumnInfo(name = "genre")
    @SerializedName("Genre")
    val genre: String,

    @ColumnInfo(name = "actors")
    @SerializedName("Actors")
    val actors: String,

    @ColumnInfo(name = "awards")
    @SerializedName("Awards")
    val awards: String,

    @ColumnInfo(name = "writer")
    @SerializedName("Writer")
    val writer: String,

    @ColumnInfo(name = "website")
    @SerializedName("Website")
    val website: String,

    @ColumnInfo(name = "ratings")
    @SerializedName("Ratings")
    val ratings: List<Rating>,

    @ColumnInfo(name = "runtime")
    @SerializedName("Runtime")
    val runtime: String,

    @ColumnInfo(name = "country")
    @SerializedName("Country")
    val country: String,

    @ColumnInfo(name = "language")
    @SerializedName("Language")
    val language: String,

    @ColumnInfo(name = "released")
    @SerializedName("Released")
    val released: String,

    @ColumnInfo(name = "director")
    @SerializedName("Director")
    val director: String,

    @ColumnInfo(name = "meta_score")
    @SerializedName("Metascore")
    val metaScore: String,

    @ColumnInfo(name = "box_office")
    @SerializedName("BoxOffice")
    val boxoffice: String,

    @ColumnInfo(name = "imdb_votes")
    @SerializedName("imdbVotes")
    val imdbvotes: String,

    @ColumnInfo(name = "imdb_rating")
    @SerializedName("imdbRating")
    val imdbrating: String,

    @ColumnInfo(name = "production")
    @SerializedName("Production")
    val production: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0

    @Ignore
    @SerializedName("Response")
    var response: Boolean = true

    class Rating(

        @SerializedName("Value")
        val value: String,

        @SerializedName("Source")
        val source: String
    )
}