package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.util.StringProvider

class ItemPrayerViewModel (val data: PrayerDataModel) {

    var position = ObservableField(0)
    val size = ObservableField(String.format(StringProvider.getInstance().getString(LocaleConstants.N_DUA),data.data?.size.toString()))
    val title = ObservableField("")
    val index = ObservableField("")
}