package com.tp.orchid.data.remote.search


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tp.orchid.BR
import com.tp.orchid.R
import com.tp.orchid.data.remote.base.BaseOmdbApiResponse
import com.tp.orchid.models.KeyValue
import java.io.Serializable
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
            Index("mv_imdb_id", unique = true)
        ]
    )
    data class Movie(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "mv_id")
        val id: Long,

        @ColumnInfo(name = "mv_type")
        @SerializedName("Type")
        val type: String,

        @ColumnInfo(name = "mv_year")
        @SerializedName("Year")
        val year: String,

        @ColumnInfo(name = "mv_title")
        @SerializedName("Title")
        var title: String,

        @ColumnInfo(name = "mv_imdb_id")
        @SerializedName("imdbID")
        val imdbId: String,

        @ColumnInfo(name = "mv_poster_url")
        @SerializedName("Poster")
        val poster: String

    ) : BaseObservable(), Serializable {


        fun getDetailsAsKeyValues(): MutableList<KeyValue> {
            val list = mutableListOf<KeyValue>()
            list.add(KeyValue(R.string.key_value_title, title))
            list.add(KeyValue(R.string.key_value_type, type))
            list.add(KeyValue(R.string.key_value_year, year))
            return list
        }

        @ColumnInfo(name = "mv_created_at")
        lateinit var createdAt: Date

        @ColumnInfo(name = "mv_updated_at")
        var updatedAt: Date? = null

        @ColumnInfo(name = "mv_is_fav")
        @get:Bindable
        var isFav: Boolean = false
            set(value) {
                updatedAt = Date()
                field = value
                notifyPropertyChanged(BR.fav)
            }

        companion object {
            const val KEY = "Movie"
        }
    }
}