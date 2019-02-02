package com.tp.theah64.orchidexample.data.local.typeconverters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tp.theah64.orchidexample.data.remote.getmovie.GetMovieResponse
import javax.inject.Inject

class RatingsConverter {

    @Inject
    @TypeConverter
    fun fromRatingListToJsonString(rating: List<GetMovieResponse.Rating>): String {
        val gson = GsonBuilder().create()
        return gson.toJson(rating)
    }

    @TypeConverter
    fun fromJsonStringToRatingList(jsonString: String): List<GetMovieResponse.Rating> {
        val gson = GsonBuilder().create()
        val listType = object : TypeToken<ArrayList<GetMovieResponse.Rating>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}