package com.tp.theah64.orchid.data.local.typeconverters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(long: Long?): Date? {
        return long?.let { Date(it) }
    }
}