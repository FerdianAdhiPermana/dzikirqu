package com.mayburger.dzikirqu.model

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@IgnoreExtraProperties
@Parcelize
class PrayerTime(var address: String? = "", var fajr: String?="", var sunrise: String? = "", var dhuhr: String? = "", var asr: String? = "", var sunset: String? = "", var maghrib: String? = "", var isya: String? = ""):
    Parcelable

