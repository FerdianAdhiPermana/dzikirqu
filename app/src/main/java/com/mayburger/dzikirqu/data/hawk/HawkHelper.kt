package com.mayburger.dzikirqu.data.hawk

import com.google.type.LatLng
import com.mayburger.dzikirqu.model.PrayerTime


interface HawkHelper {
    var language:String
    var praytime:PrayerTime
    var userCoordinates:LatLng?
    var userCity:String
}