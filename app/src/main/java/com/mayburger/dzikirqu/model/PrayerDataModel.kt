package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
@Entity(tableName = "book")
class PrayerDataModel(

    @ColumnInfo(name = "id")
    var id: String? = null,
    @ColumnInfo(name = "language")
    var language: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    var data: List<Data>? = ArrayList()
) : Parcelable {
    @Parcelize
    class Data(
        @ColumnInfo(name = "arabic")
        var arabic: String? = null,
        @ColumnInfo(name = "translation")
        var translation: String? = null,
        @ColumnInfo(name = "source")
        var source: String? = null,
        @ColumnInfo(name = "notes")
        var notes: String? = null,
        @ColumnInfo(name = "audio")
        var audio: String? = null
    ) : Parcelable

}

