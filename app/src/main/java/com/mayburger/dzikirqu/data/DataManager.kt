package com.mayburger.dzikirqu.data

import com.mayburger.dzikirqu.data.firebase.FirebaseHelper
import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.model.PrayerTime

interface DataManager : HawkHelper, FirebaseHelper{
    suspend fun getPrayerTime():PrayerTime
}