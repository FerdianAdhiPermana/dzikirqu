package com.mayburger.dzikirqu.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.model.SurahJsonModel
import com.orhanobut.hawk.Hawk

object QuranUtils {
    fun getSurahFromJson(context: Context):List<SurahDataModel>{
        val language = Hawk.get(AppHawkHelper.HAWK_KEY_LANGUAGE,"en")
        val json = Gson().fromJson<ArrayList<SurahJsonModel>>(
            FileUtils.getJsonStringFromAssets(
                context,
                "json/lang/$language.json"
            ),
            object : TypeToken<ArrayList<SurahJsonModel>>() {}.type
        )
        return json.map{data->
            SurahDataModel(
                data.chapter_number,
                data.name_simple,
                data.name_arabic,
                data.translated_name.name,
                data.revelation_place,
                data.verses_count, language
            )
        }.toCollection(arrayListOf())
    }
}