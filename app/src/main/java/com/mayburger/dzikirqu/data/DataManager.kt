package com.mayburger.dzikirqu.data

import com.mayburger.dzikirqu.data.firebase.FirebaseHelper
import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.data.room.RoomHelper
import com.mayburger.dzikirqu.model.PrayerTime

interface DataManager : HawkHelper, FirebaseHelper,RoomHelper{
    suspend fun getPrayerTime():PrayerTime
}