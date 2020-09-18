package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.PrayerDataModel

class ItemPrayerViewModel (val data: PrayerDataModel) {

    var position = ObservableField(0)
    val size = ObservableField("${data.data?.size} Items")
    val title = ObservableField("")
}