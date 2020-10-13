package com.mayburger.dzikirqu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by Mayburger on 10/12/18.
 */
@Parcelize
class QuranLastRead(
    val surah:SurahDataModel,
    val verse:AyahDataModel
) : Parcelable