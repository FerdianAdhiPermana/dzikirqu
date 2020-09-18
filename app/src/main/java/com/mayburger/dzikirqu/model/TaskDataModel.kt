package com.mayburger.dzikirqu.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.util.*


/**
 * Created by Mayburger on 10/12/18.
 */
@IgnoreExtraProperties
@Parcelize
@Entity(tableName = "task")
class TaskDataModel(
    @ColumnInfo(name="title")
    var title:String,
    @ColumnInfo(name="totalTask")
    var totalTask:Int,
    @ColumnInfo(name="taskCount")
    var taskCount:Int,
    @ColumnInfo(name="disabled")
    var disabled:Boolean,
    @ColumnInfo(name="date")
    var date:Date
) : Parcelable{
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}