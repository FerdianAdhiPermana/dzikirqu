package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.PrayerDataModel

class ItemPrayerViewModel (val data: PrayerDataModel) {

    val size = ObservableField("${data.data?.size} Items")
}