package com.tp.orchid.data.remote.search

import com.your.packagename.api.responses.BaseOmdbApiResponse


import com.google.gson.annotations.SerializedName

/**
 * Generated using MockAPI (https://github.com/theapache64/Mock-API) : Fri Jan 18 10:57:12 UTC 2019
 */
class SearchResponse(
    @SerializedName("Search") val search: List<Search>,
    @SerializedName("totalResults") val totalResults: Int,
    error: String, response: Boolean
) : BaseOmdbApiResponse(error, response) {

    class Search(
        @SerializedName("Type") val type: String,
        @SerializedName("Year")val year: String,
        @SerializedName("Title") val title: String,
        @SerializedName("imdbID") val imdbId: String,
        @SerializedName("Poster") val poster: String
    )
}