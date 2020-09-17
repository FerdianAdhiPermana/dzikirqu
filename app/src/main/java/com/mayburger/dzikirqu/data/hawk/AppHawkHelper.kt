package com.mayburger.dzikirqu.data.hawk

import com.google.type.LatLng
import com.mayburger.dzikirqu.model.PrayerTime
import com.orhanobut.hawk.Hawk
import javax.inject.Inject

class AppHawkHelper @Inject constructor() : HawkHelper {


    private val TAG = this.javaClass.simpleName

    companion object {
        const val HAWK_KEY_LANGUAGE = "hawk_language"
        const val HAWK_KEY_PRAYERTIME = "hawk_praytime"
        const val HAWK_KEY_USER_COORDINATES = "hawk_user_coordinates"
        const val HAWK_KEY_USER_CITY = "hawk_user_city"
    }

    override var language: String
        get() = Hawk.get(HAWK_KEY_LANGUAGE, "en")
        set(value) {
            Hawk.put(HAWK_KEY_LANGUAGE, value)
        }
    override var praytime: PrayerTime
        get() = Hawk.get(HAWK_KEY_PRAYERTIME, PrayerTime())
        set(value) {
            Hawk.put(HAWK_KEY_PRAYERTIME,value)
        }
    override var userCoordinates: LatLng?
        get() = Hawk.get(HAWK_KEY_USER_COORDINATES,null)
        set(value) {
            Hawk.put(HAWK_KEY_USER_COORDINATES,value)
        }
    override var userCity: String
        get() = Hawk.get(HAWK_KEY_USER_CITY,"")
        set(value) {
            Hawk.put(HAWK_KEY_USER_CITY,value)
        }


}