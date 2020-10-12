package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.SurahDataModel

class PagePrayerQuranViewModel(
    val data: PrayerDataModel.Data,
    val surah :SurahDataModel
) :
    ViewModel() {

    val surahId = MutableLiveData(surah.id.toString())

}