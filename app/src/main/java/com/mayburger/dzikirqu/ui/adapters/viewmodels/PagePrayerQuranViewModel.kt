package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.SurahDataModel

class PagePrayerQuranViewModel(
    val data: PrayerDataModel.Data,
    val surah: SurahDataModel,
    val navigator:Callback
) :
    ViewModel() {

    interface Callback{
        fun onClickItem(surah:SurahDataModel)
    }

    val surahId = MutableLiveData(surah.id.toString())

    fun openQuran() {
        navigator.onClickItem(surah)
    }
}