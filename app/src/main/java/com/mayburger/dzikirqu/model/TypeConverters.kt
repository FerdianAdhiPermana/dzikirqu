package com.mayburger.dzikirqu.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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