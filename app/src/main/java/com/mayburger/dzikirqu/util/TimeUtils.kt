package com.mayburger.dzikirqu.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun getDateFromPrayerTime(date:String): Date {
        return SimpleDateFormat("HH:mm").parse(date)
    }

    fun isTimeValid(context: Context):Boolean{
        return android.provider.Settings.Global.getInt(context.contentResolver, android.provider.Settings.Global.AUTO_TIME, 0) == 1
    }
}