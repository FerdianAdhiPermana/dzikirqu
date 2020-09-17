package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by Mayburger on 10/12/18.
 */
@IgnoreExtraProperties
@Entity(tableName = "book")
@Parcelize
class BookDataModel(
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("desc")
    var desc: String = "",
    @SerializedName("updated")
    var updated: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("language")
    var language: String = "",
    @SerializedName("status")
    var status: String = ""
) : Parcelable