package com.mayburger.dzikirqu.util


import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import io.reactivex.Single
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SingleLocationProvider {

    // calls back to calling thread, note this is for low grain: if you want higher precision, swap the
    // contents of the else and if. Also be sure to check gps permission/settings are allowed.
    // call usually takes <10ms
    @SuppressLint("MissingPermission", "CheckResult")
    suspend fun requestSingleUpdate(context: Context): GPSCoordinates {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null && location.time > Calendar.getInstance().timeInMillis - 2 * 60 * 1000) {
            return GPSCoordinates(location.latitude, location.longitude)
        } else {
            return when {
                isNetworkEnabled -> {
                    val criteria = Criteria()
                    criteria.accuracy = Criteria.ACCURACY_COARSE
                    return suspendCoroutine { cont ->
                        locationManager.requestSingleUpdate(criteria, {
                            cont.resume(
                                GPSCoordinates(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        }, null)
                    }
                }
                isGPSEnabled -> {
                    val criteria = Criteria()
                    criteria.accuracy = Criteria.ACCURACY_FINE
                    return suspendCoroutine { cont ->
                        locationManager.requestSingleUpdate(criteria, {
                            cont.resume(
                                GPSCoordinates(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        }, null)
                    }
                }
                else -> {
                    GPSCoordinates(0.0, 0.0)
                }
            }
        }
    }


    // consider returning Location instead of this dummy wrapper class
    class GPSCoordinates(theLatitude: Double, theLongitude: Double) {
        var longitude = -1f
        var latitude = -1f

        init {
            longitude = theLongitude.toFloat()
            latitude = theLatitude.toFloat()
        }
    }
}
