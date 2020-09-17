package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize


/**
 * Created by Mayburger on 10/12/18.
 */
@IgnoreExtraProperties
@Entity(tableName = "book")
@Parcelize
class BookDataModel(
    var title: String = "",
    var desc: String = "",
    var updated: String = "",
    var type: String = "",
    var id: String = "",
    var language: String = "",
    var status: String = "",
    var prayer:ArrayList<PrayerDataModel> = ArrayList()
) : Parcelable{
}