package com.tp.orchid.data.remote.getmovie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tp.orchid.R
import com.tp.orchid.data.remote.base.BaseOmdbApiResponse
import com.tp.orchid.models.KeyValue

/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Tue Jan 22 07:49:00 UTC 2019
 */
@Entity(tableName = "movie_details")
class GetMovieResponse(

    @ColumnInfo(name = "dvd")
    @SerializedName("DVD")
    val dvd: String?,

    @ColumnInfo(name = "plot")
    @SerializedName("Plot")
    val plot: String?,

    @ColumnInfo(name = "rated")
    @SerializedName("Rated")
    val rated: String?,

    @ColumnInfo(name = "genre")
    @SerializedName("Genre")
    val genre: String?,

    @ColumnInfo(name = "actors")
    @SerializedName("Actors")
    val actors: String?,

    @ColumnInfo(name = "awards")
    @SerializedName("Awards")
    val awards: String?,

    @ColumnInfo(name = "writer")
    @SerializedName("Writer")
    val writer: String?,

    @ColumnInfo(name = "website")
    @SerializedName("Website")
    val website: String?,

    @ColumnInfo(name = "ratings")
    @SerializedName("Ratings")
    val ratings: List<Rating>,

    @ColumnInfo(name = "runtime")
    @SerializedName("Runtime")
    val runtime: String?,

    @ColumnInfo(name = "country")
    @SerializedName("Country")
    val country: String?,

    @ColumnInfo(name = "language")
    @SerializedName("Language")
    val language: String?,

    @ColumnInfo(name = "released")
    @SerializedName("Released")
    val released: String?,

    @ColumnInfo(name = "director")
    @SerializedName("Director")
    val director: String?,

    @ColumnInfo(name = "meta_score")
    @SerializedName("Metascore")
    val metaScore: String?,

    @ColumnInfo(name = "box_office")
    @SerializedName("BoxOffice")
    val boxoffice: String?,

    @ColumnInfo(name = "imdb_votes")
    @SerializedName("imdbVotes")
    val imdbvotes: String?,

    @ColumnInfo(name = "imdb_rating")
    @SerializedName("imdbRating")
    val imdbrating: String?,

    @ColumnInfo(name = "production")
    @SerializedName("Production")
    val production: String?,

    error: String?,
    response: Boolean

) : BaseOmdbApiResponse(error, response) {
    fun getDetailsAsKeyValues(): List<KeyValue> {

        val list = mutableListOf<KeyValue>()
        list.add(KeyValue(R.string.key_value_dvd, dvd))
        list.add(KeyValue(R.string.key_value_plot, plot))
        list.add(KeyValue(R.string.key_value_rated, rated))
        list.add(KeyValue(R.string.key_value_genre, genre))
        list.add(KeyValue(R.string.key_value_actors, actors))
        list.add(KeyValue(R.string.key_value_awards, awards))
        list.add(KeyValue(R.string.key_value_writer, writer))
        list.add(KeyValue(R.string.key_value_website, website))

        // looping through ratings
        ratings.forEach {
            val value = "${it.source} : ${it.value}"
            list.add(KeyValue(R.string.key_value_rating, value))
        }

        list.add(KeyValue(R.string.key_value_runtime, runtime))
        list.add(KeyValue(R.string.key_value_country, country))
        list.add(KeyValue(R.string.key_value_language, language))
        list.add(KeyValue(R.string.key_value_released, released))
        list.add(KeyValue(R.string.key_value_director, director))
        list.add(KeyValue(R.string.key_value_metascore, metaScore))
        list.add(KeyValue(R.string.key_value_box_office, boxoffice))
        list.add(KeyValue(R.string.key_value_imdb_votes, imdbvotes))
        list.add(KeyValue(R.string.key_value_imdb_rating, imdbrating))
        list.add(KeyValue(R.string.key_value_production, production))

        return list
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0

    class Rating(

        @SerializedName("Value")
        val value: String?,

        @SerializedName("Source")
        val source: String
    )
}