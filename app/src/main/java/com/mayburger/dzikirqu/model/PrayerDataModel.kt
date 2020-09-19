package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
@Entity(tableName = "prayer")
class PrayerDataModel(
    @ColumnInfo(name = "ids")
    @PrimaryKey(autoGenerate = true)
    var ids: Int? = null,
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "book_id")
    var bookId: Int = 0,
    @ColumnInfo(name = "language")
    var language: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "data")
    var data: List<Data>? = ArrayList()
) : Parcelable {
    @Parcelize
    class Data(
        var arabic: String? = null,
        var translation: String? = null,
        var source: String? = null,
        var notes: String? = null,
        var audio: String? = null
    ) : Parcelable

}

