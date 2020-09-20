package com.mayburger.dzikirqu.util

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper.Companion.HAWK_KEY_LANGUAGE
import com.orhanobut.hawk.Hawk
import java.util.*


class StringProvider(private var context: Context?) {


    private var mStringMap: HashMap<String, String>? = null

    init {
        initRawString()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var ins: StringProvider? = null

        @Synchronized
        fun getInstance(): StringProvider {
            return ins!!
        }

        fun init(context: Context) {
            ins = StringProvider(context)
        }
    }

    fun getString(key: String): String {
        val language = Hawk.get(HAWK_KEY_LANGUAGE,"id")
        val jsonString = context?.resources?.getIdentifier("locale_$language", "raw", context?.packageName)?.let {
            context?.resources?.openRawResource(it)?.bufferedReader().use { it?.readText() }
        }
        val jsonElement = Gson().fromJson(jsonString, JsonElement::class.java)
        val json = jsonElement.asJsonObject
        for (entry in json.entrySet()) {
            if (entry.key == key) {
                return entry.value.asString
            }
        }
        return key
    }

    fun initRawString(): String {
        return try {
            val jsonString = context?.resources?.openRawResource(com.mayburger.dzikirqu.R.raw.locale_id)
                    ?.bufferedReader().use { it?.readText() }
            val jsonElement = Gson().fromJson<JsonElement>(jsonString, JsonElement::class.java)
            val json = jsonElement.asJsonObject
            for (entry in json.entrySet()) {
            }
            ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}
