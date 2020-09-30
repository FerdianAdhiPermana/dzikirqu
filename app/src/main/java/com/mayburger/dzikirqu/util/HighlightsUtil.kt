package com.mayburger.dzikirqu.util

import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper
import com.mayburger.dzikirqu.model.HighlightDataModel
import com.mayburger.dzikirqu.model.PrayerTime
import com.orhanobut.hawk.Hawk

object HighlightsUtil {
    fun getDefaultHighlights(language:String): ArrayList<HighlightDataModel> {
        val prayTime = Hawk.get(AppHawkHelper.HAWK_KEY_PRAYERTIME, PrayerTime())
        val highlights = ArrayList<HighlightDataModel>()
        if (prayTime.address != "") {
            highlights.add(
                HighlightDataModel(
                    LocaleConstants.MORNING_DZIKIR,
                    LocaleConstants.BOOK_OF_DZIKIR,
                    1,
                    1,
                    language=language,
                    type=0
                )
            )
            highlights.add(
                HighlightDataModel(
                    LocaleConstants.EVENING_DZIKIR,
                    LocaleConstants.BOOK_OF_DZIKIR,
                    1,
                    2,
                    language=language,
                    type=0
                )
            )
            highlights.add(
                HighlightDataModel(
                    LocaleConstants.DZIKIR_AFTER_SHALAT,
                    LocaleConstants.BOOK_OF_DZIKIR,
                    1,
                    3,
                    language=language,
                    type=0
                )
            )
            highlights.add(
                HighlightDataModel(
                    LocaleConstants.DZIKIR_BEFORE_SLEEP,
                    LocaleConstants.BOOK_OF_DZIKIR,
                    1,
                    4,
                    language=language,
                    type=0
                )
            )
        }
        return highlights
    }
}