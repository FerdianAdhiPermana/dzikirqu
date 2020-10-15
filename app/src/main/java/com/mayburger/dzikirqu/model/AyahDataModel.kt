package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


/**
 * Created by Mayburger on 10/12/18.
 */
@IgnoreExtraProperties
@Entity(tableName = "ayah")
@Parcelize
class AyahDataModel(
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "juz")
    var juz: Int = 0,
    @ColumnInfo(name = "surahId")
    var surahId: Int = 0,
    @ColumnInfo(name = "arabic")
    var arabic: String = "",
    @ColumnInfo(name = "translation")
    var translation: String = "",
    @ColumnInfo(name = "language")
    var language:String = "",
    @ColumnInfo(name= "useBismillah")
    var use_bismillah:Boolean? = false,
) : Parcelable{
    @ColumnInfo(name = "ids")
    @PrimaryKey(autoGenerate = true)
    var ids: Int? = null
    @IgnoredOnParcel
    @Ignore
    var surah:SurahDataModel?=null
}
