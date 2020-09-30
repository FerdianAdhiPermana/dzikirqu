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
@Entity(tableName = "surah")
@Parcelize
class SurahDataModel(
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "arabic")
    var arabic: String = "",
    @ColumnInfo(name = "translation")
    var translation: String = "",
    @ColumnInfo(name="revelation")
    var revelation:String = "",
    @ColumnInfo(name = "verses")
    var verses: Int = 0,
    @ColumnInfo(name = "language")
    var language: String = ""
) : Parcelable{
    @ColumnInfo(name = "ids")
    @PrimaryKey(autoGenerate = true)
    var ids: Int? = null
    @ColumnInfo(name="verse")
    var verse:Int = 0
}