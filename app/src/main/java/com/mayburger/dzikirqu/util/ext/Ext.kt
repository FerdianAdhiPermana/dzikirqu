package com.mayburger.dzikirqu.util.ext

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


fun Int.toStringJson(mContext: Context): String {
    val `is`: InputStream = mContext.resources.openRawResource(this)
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    `is`.use { `is` ->
        val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
    }
    return writer.toString()
}

fun <T> String.jsonToArraylistObject():ArrayList<T>{
    val json = this
    return Gson().fromJson(json, object: TypeToken<ArrayList<T>>(){}.type)
}

fun <T> LiveData<T>.getObserverValue(owner: LifecycleOwner):T?{
    var returnValue:T? = null
    this.observe(owner, {
        returnValue = it
    })
    return returnValue
}



