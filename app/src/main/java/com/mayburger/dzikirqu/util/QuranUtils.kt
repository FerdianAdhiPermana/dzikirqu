package com.mayburger.dzikirqu.util

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.model.SurahJsonModel
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.tasks.await

object QuranUtils {
    fun getSurahFromJson(context: Context): List<SurahDataModel> {
        val language = Hawk.get(AppHawkHelper.HAWK_KEY_LANGUAGE, "en")
        val json = Gson().fromJson<ArrayList<SurahJsonModel>>(
            FileUtils.getJsonStringFromAssets(
                context,
                "json/lang/$language.json"
            ),
            object : TypeToken<ArrayList<SurahJsonModel>>() {}.type
        )
        return json.map { data ->
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

    suspend fun overridePrayer(context:Context){
        val books = Firebase.firestore.collection("books").get().await().documents
        val json = Gson().fromJson<ArrayList<BookDataModel>>(
            FileUtils.getJsonStringFromRaw(
                context,
                R.raw.dzikir
            ),
            object : TypeToken<ArrayList<BookDataModel>>() {}.type
        )
        books.map{document->
            val book = document.toObject(BookDataModel::class.java)
            json.map{
                if(book?.language == it.language && book.id == it.id){
                    Firebase.firestore.collection("books").document(document.id).set(it)
                }
            }
        }
    }
}