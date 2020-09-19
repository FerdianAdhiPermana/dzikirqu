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
@Parcelize
@Entity(tableName = "highlight")
class HighlightDataModel(
    @ColumnInfo(name="title")
    var title:String,
    @ColumnInfo(name="bookTitle")
    var bookTitle:String,
    @ColumnInfo(name="bookId")
    var bookId:Int,
    @ColumnInfo(name="prayerId")
    var prayerId:Int,
    @ColumnInfo(name="type")
    var type:Int
) : Parcelable{
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}