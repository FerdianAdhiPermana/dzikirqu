package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@IgnoreExtraProperties
@Entity(tableName = "book")
class PrayerDataModele : Serializable {

    @PrimaryKey(autoGenerate = true)
    var ids: Int = 0
    @SerializedName("data")
    var data: List<Data>? = null
    @SerializedName("type")
    @ColumnInfo(name = "type")
    var type: String? = null

    @Parcelize
    class Data(@SerializedName("id")
               @ColumnInfo(name = "id")
               var id: String? = null,
               @SerializedName("language")
               @ColumnInfo(name = "language")
               var language: String? = null,
               @SerializedName("title")
               @ColumnInfo(name = "title")
               var title: String? = null,
               @SerializedName("type")
               @ColumnInfo(name = "type")
               var type: String? = null,
               @SerializedName("results")
               var results: List<Results>? = null)  :Parcelable {
        @Parcelize
        class Results : Parcelable {
            @SerializedName("arabic")
            @ColumnInfo(name = "arabic")
            var arabic: String? = null
            @SerializedName("translation")
            @ColumnInfo(name = "translation")
            var translation: String? = null
            @SerializedName("source")
            @ColumnInfo(name = "source")
            var source: String? = null
            @SerializedName("recite")
            @ColumnInfo(name = "recite")
            var recite: String? = null
            @SerializedName("audio")
            @ColumnInfo(name = "audio")
            var audio: String? = null
        }
    }
}

