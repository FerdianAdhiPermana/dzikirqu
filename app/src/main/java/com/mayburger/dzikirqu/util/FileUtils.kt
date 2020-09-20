package com.mayburger.dzikirqu.util

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*

object FileUtils {

    fun getJsonStringFromRaw(context: Context, rawInt: Int): String {
        val `is`: InputStream = context.resources.openRawResource(rawInt)
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

    fun writeStringToFile(data: String,fileName:String) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(FileOutputStream(
                    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/$fileName.json"
                ))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    fun getJsonStringFromAssets(context: Context, assetPath: String):String{
        val `is`: InputStream = context.assets.open(assetPath)
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

}