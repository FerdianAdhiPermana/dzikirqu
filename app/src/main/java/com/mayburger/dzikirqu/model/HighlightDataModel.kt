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
    @ColumnInfo(name="subtitle")
    var subtitle:String,
    @ColumnInfo(name="id")
    var id:Int,
    @ColumnInfo(name="content_id")
    var contentId:Int,
    @ColumnInfo(name="sub_content_id")
    var subContentId:Int?=null,
    @ColumnInfo(name="language")
    var language:String,
    @ColumnInfo(name="type")
    var type:Int
) : Parcelable{
    @ColumnInfo(name = "ids")
    @PrimaryKey(autoGenerate = true)
    var ids: Int? = null
}