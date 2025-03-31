package com.example.cryptotransactionviewer.data.local.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    // Convert List<String> to JSON string for storage
    @TypeConverter
    fun fromStringList(list: List<String>?): String?{
        return  list.let { gson.toJson(it) }
    }

    // Convert JSON string back to List<String> when reading from database
    @TypeConverter
    fun toStringList(json: String?): List<String>?{
        if (json == null) return null
        val listType = object:TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }

//    Converts timeStamp to readable date
    @TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date? {
        return value?.let { java.util.Date(it) }
    }

    //    Converts date to timestamp date

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? {
        return date?.time
    }
}