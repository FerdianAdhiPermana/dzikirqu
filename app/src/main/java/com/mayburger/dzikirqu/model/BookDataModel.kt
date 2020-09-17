package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize


/**
 * Created by Mayburger on 10/12/18.
 */
@IgnoreExtraProperties
@Entity(tableName = "book")
@Parcelize
class BookDataModel(
    @ColumnInfo(name = "ids")
    @PrimaryKey(autoGenerate = true)
    var ids: Int? = null,
    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "desc")
    var desc: String = "",
    @ColumnInfo(name = "updated")
    var updated: String = "",
    @ColumnInfo(name = "type")
    var type: String = "",
    @ColumnInfo(name = "language")
    var language: String = "",
    @ColumnInfo(name = "status")
    var status: String = "",
    @ColumnInfo(name = "prayer")
    var prayer:List<PrayerDataModel> = ArrayList()
) : Parcelable{
}