package com.mayburger.dzikirqu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by Mayburger on 10/12/18.
 */
@Parcelize
class LinkDataModel(
    var value:String?=null,
    var bookType:Int?=null,
    var bookId:Int?=null
) : Parcelable
