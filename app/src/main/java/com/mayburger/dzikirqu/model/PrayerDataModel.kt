package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
@Entity(tableName = "book")
class PrayerDataModel(

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
    @SerializedName("data")
    var data: List<Data>? = null
) : Parcelable {
    @Parcelize
    class Data(
        @SerializedName("arabic")
        @ColumnInfo(name = "arabic")
        var arabic: String? = null,
        @SerializedName("translation")
        @ColumnInfo(name = "translation")
        var translation: String? = null,
        @SerializedName("source")
        @ColumnInfo(name = "source")
        var source: String? = null,
        @SerializedName("notes")
        @ColumnInfo(name = "notes")
        var notes: String? = null,
        @SerializedName("audio")
        @ColumnInfo(name = "audio")
        var audio: String? = null
    ) : Parcelable

    companion object {
        fun create(prayer: PrayerDataModele.Data, book: BookDataModel):PrayerDataModel{
            return PrayerDataModel(
                id = book.id,
                language = book.language,
                title = book.title,
                type = book.type,
                data = prayer.results?.map {
                    Data(
                        arabic = it.arabic,
                        translation = it.translation,
                        source = it.source,
                        notes = it.recite,
                        audio = it.audio
                    )
                }?.toCollection(arrayListOf())
            )
        }
    }
}

