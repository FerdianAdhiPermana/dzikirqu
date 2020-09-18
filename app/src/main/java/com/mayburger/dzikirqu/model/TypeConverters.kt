package com.mayburger.dzikirqu.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PrayerTypeConverter {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<PrayerDataModel> {
        val gson = Gson()
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<PrayerDataModel>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<PrayerDataModel>): String {
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}
class DataTypeConverter {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<PrayerDataModel.Data> {
        val gson = Gson()
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<PrayerDataModel.Data>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<PrayerDataModel.Data>): String {
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}

class DateConverter {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return null
    }

    @TypeConverter
    fun dateToTimestamp(value: Date?): String? {
        return if (value == null) null else df.format(value)
    }
}