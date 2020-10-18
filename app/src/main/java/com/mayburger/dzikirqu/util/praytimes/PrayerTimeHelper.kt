package com.mayburger.dzikirqu.util.praytimes

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import androidx.databinding.ObservableField
import com.google.type.LatLng
import com.mayburger.dzikirqu.constants.LocaleConstants.ASR
import com.mayburger.dzikirqu.constants.LocaleConstants.DHUHR
import com.mayburger.dzikirqu.constants.LocaleConstants.FAJR
import com.mayburger.dzikirqu.constants.LocaleConstants.HOUR
import com.mayburger.dzikirqu.constants.LocaleConstants.ISYA
import com.mayburger.dzikirqu.constants.LocaleConstants.LEFT_UNTIL
import com.mayburger.dzikirqu.constants.LocaleConstants.MAGHRIB
import com.mayburger.dzikirqu.constants.LocaleConstants.MINUTE
import com.mayburger.dzikirqu.constants.LocaleConstants.NEXT_PRAYER_S
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper.Companion.HAWK_KEY_PRAYERTIME
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper.Companion.HAWK_KEY_USER_CITY
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper.Companion.HAWK_KEY_USER_COORDINATES
import com.mayburger.dzikirqu.model.PrayerTime
import com.mayburger.dzikirqu.util.SingleLocationProvider
import com.mayburger.dzikirqu.util.StringProvider
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Created by Mayburger on 2/15/19.
 */

interface PrayerTimeHelper {

    fun getPrayerTime(ctx: Context): Observable<PrayerTime>

    companion object {
        @SuppressLint("CheckResult")
        suspend fun getPrayerTime(ctx: Context): PrayerTime {
            val location = SingleLocationProvider.requestSingleUpdate(ctx)
            return buildPrayerTime(
                ctx,
                LatLng.newBuilder().setLatitude(location.latitude.toDouble())
                    .setLongitude(location.longitude.toDouble()).build()
            )
        }

        fun getPrayerTimeFromHawk(): PrayerTime {
            val defaultValue = PrayerTime(
                "Loading...",
                "00:00",
                "00:00",
                "00:00",
                "00:00",
                "00:00",
                "00:00",
                "00:00"
            )
            return Hawk.get(HAWK_KEY_PRAYERTIME, defaultValue)
        }

        fun putToHawk(ctx: Context, prayerTime: PrayerTime) {
            Hawk.init(ctx).build()
            Hawk.put(HAWK_KEY_PRAYERTIME, prayerTime)
        }

        fun isNetworkAvailable(ctx: Context): Boolean {
            val connectivityManager =
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        fun buildPrayerUntil(
            prayerUntilTime: ObservableField<String>,
            now: String,
            p1: PrayerTime
        ): String {
            when {
                now < p1.fajr!! -> {
                    prayerUntilTime.set(
                        countTimeLight(
                            p1.fajr!!,
                            StringProvider.getInstance().getString(FAJR)
                        )
                    )
                    return StringProvider.getInstance().getString(FAJR)
                }
                now < p1.dhuhr!! -> {
                    prayerUntilTime.set(
                        countTimeLight(
                            p1.dhuhr!!,
                            StringProvider.getInstance().getString(DHUHR)
                        )
                    )
                    return StringProvider.getInstance().getString(DHUHR)
                }
                now < p1.asr!! -> {
                    prayerUntilTime.set(
                        countTimeLight(
                            p1.asr!!,
                            StringProvider.getInstance().getString(ASR)
                        )
                    )
                    return StringProvider.getInstance().getString(ASR)
                }
                now < p1.maghrib!! -> {
                    prayerUntilTime.set(
                        countTimeLight(
                            p1.maghrib!!,
                            StringProvider.getInstance().getString(MAGHRIB)
                        )
                    )
                    return StringProvider.getInstance().getString(MAGHRIB)
                }
                now < p1.isya!! -> {
                    prayerUntilTime.set(
                        countTimeLight(
                            p1.isya!!,
                            StringProvider.getInstance().getString(ISYA)
                        )
                    )
                    return StringProvider.getInstance().getString(ISYA)
                }
                else -> {
                    prayerUntilTime.set(
                        countTimeLight(
                            p1.fajr!!,
                            StringProvider.getInstance().getString(FAJR)
                        )
                    )
                    return StringProvider.getInstance().getString(FAJR)
                }
            }
        }

        fun getCurrentPrayer(now: String, p1: PrayerTime): String {
            return when {
                now < p1.fajr!! -> StringProvider.getInstance().getString(FAJR)
                now < p1.dhuhr!! -> StringProvider.getInstance().getString(DHUHR)
                now < p1.asr!! -> StringProvider.getInstance().getString(ASR)
                now < p1.maghrib!! -> StringProvider.getInstance().getString(MAGHRIB)
                now < p1.isya!! -> StringProvider.getInstance().getString(ISYA)
                else -> {
                    StringProvider.getInstance().getString(FAJR)
                }
            }
        }

        fun getNextPrayer(nextPrayer: ObservableField<String>, now: String, p1: PrayerTime) {
            when {
                now < p1.fajr!! -> nextPrayer.set(StringProvider.getInstance().getString(FAJR))
                now < p1.dhuhr!! -> nextPrayer.set(StringProvider.getInstance().getString(DHUHR))
                now < p1.asr!! -> nextPrayer.set(StringProvider.getInstance().getString(ASR))
                now < p1.maghrib!! -> nextPrayer.set(
                    StringProvider.getInstance().getString(MAGHRIB)
                )
                now < p1.isya!! -> nextPrayer.set(StringProvider.getInstance().getString(ISYA))
                else -> {
                    nextPrayer.set(StringProvider.getInstance().getString(FAJR))
                }
            }
        }

        fun buildPrayerTime(prayerTimeText: ObservableField<String>, now: String, p1: PrayerTime) {
            when {
                now < p1.fajr!! -> prayerTimeText.set(p1.fajr)
                now < p1.dhuhr!! -> prayerTimeText.set(p1.dhuhr)
                now < p1.asr!! -> prayerTimeText.set(p1.asr)
                now < p1.maghrib!! -> prayerTimeText.set(p1.maghrib)
                now < p1.isya!! -> prayerTimeText.set(p1.isya)
                else -> {
                    prayerTimeText.set(p1.fajr)
                }
            }
        }

        fun countTime(endTime: String): String {
            val cal = Calendar.getInstance()
            val nowHour = cal.get(Calendar.HOUR_OF_DAY)
            val nowMin = cal.get(Calendar.MINUTE)
            val m = Pattern.compile("(\\d{2}):(\\d{2})").matcher(endTime)
            require(m.matches()) { "Invalid time format: $endTime" }
            val endHour = Integer.parseInt(m.group(1)!!)
            val endMin = Integer.parseInt(m.group(2)!!)
            require(!(endHour >= 24 || endMin >= 60)) { "Invalid time format: $endTime" }
            var minutesLeft = endHour * 60 + endMin - (nowHour * 60 + nowMin)
            if (minutesLeft < 0)
                minutesLeft += 24 * 60 // Time passed, so time until 'end' tomorrow
            val hours = minutesLeft / 60
            val minutes = minutesLeft - hours * 60
            return "${StringProvider.getInstance().getString(NEXT_PRAYER_S)} " +
                    "$hours ${StringProvider.getInstance().getString(HOUR)} " +
                    "$minutes ${StringProvider.getInstance().getString(MINUTE)}"
//            return hours.toString() + "h " + minutes + "m ${StringProvider.getInstance().getString(LEFT_UNTIL)} " + prayer
        }

        fun countTimeLight(endTime: String, prayer: String): String {
            val cal = Calendar.getInstance()
            val nowHour = cal.get(Calendar.HOUR_OF_DAY)
            val nowMin = cal.get(Calendar.MINUTE)
            val m = Pattern.compile("(\\d{2}):(\\d{2})").matcher(endTime)
            require(m.matches()) { "Invalid time format: $endTime" }
            val endHour = Integer.parseInt(m.group(1)!!)
            val endMin = Integer.parseInt(m.group(2)!!)
            require(!(endHour >= 24 || endMin >= 60)) { "Invalid time format: $endTime" }
            var minutesLeft = endHour * 60 + endMin - (nowHour * 60 + nowMin)
            if (minutesLeft < 0)
                minutesLeft += 24 * 60 // Time passed, so time until 'end' tomorrow
            val hours = minutesLeft / 60
            val minutes = minutesLeft - hours * 60
//            return "${StringProvider.getInstance().getString(TIME_LEFT_UNTIL)}$prayer: " +
//                    "$hours ${StringProvider.getInstance().getString(HOUR)} " +
//                    "$minutes ${StringProvider.getInstance().getString(MINUTE)}"
            return hours.toString() + "${
            StringProvider.getInstance().getString(HOUR).substring(0, 1)
            } " + minutes + "${
            StringProvider.getInstance().getString(MINUTE).substring(0, 1)
            } ${StringProvider.getInstance().getString(LEFT_UNTIL)} " + prayer
        }


        fun buildPrayerTime(ctx: Context, latLng: LatLng): PrayerTime {
            Hawk.put(HAWK_KEY_USER_COORDINATES, latLng)
            var address: String? = "Cannot get location"
            try {
                if (isNetworkAvailable(ctx)) {
                    val gcd = Geocoder(ctx, Locale.getDefault())
                    val addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1)
                    if (addresses.size > 0) {
                        address = addresses[0].locality
                        Hawk.put(HAWK_KEY_USER_CITY, address)
                    }
                } else {
                    if (Hawk.get(HAWK_KEY_USER_CITY, "") != "") {
                        address = Hawk.get(HAWK_KEY_USER_CITY)
                    }
                }
            } catch (e: Exception) {
            }

            val prayers = PrayTime()
            prayers.setTimeFormat(prayers.Time24)
            prayers.setCalcMethod(4)
            prayers.setAdjustHighLats(prayers.AngleBased)
            val offsets = intArrayOf(0, 0, 0, 0, 0, 0, 0)
            prayers.tune(offsets)
            val mCalendar = GregorianCalendar()
            val mTimeZone = mCalendar.timeZone
            val mGMTOffset = mTimeZone.rawOffset
            val now = Date()
            val cal = Calendar.getInstance()
            cal.time = now
            val prayerTimes = prayers.getPrayerTimes(
                cal,
                latLng.latitude,
                latLng.longitude,
                TimeUnit.HOURS.convert(mGMTOffset.toLong(), TimeUnit.MILLISECONDS).toDouble()
            )
            val prayerTime = PrayerTime(
                address,
                prayerTimes[0],
                prayerTimes[1],
                prayerTimes[2],
                prayerTimes[3],
                prayerTimes[4],
                prayerTimes[5],
                prayerTimes[6]
            )
            putToHawk(ctx, prayerTime)
            return prayerTime
        }
    }
}
