package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.PrayerDataModele

class ItemBookListViewModel (val data:PrayerDataModele.Data) {

    val size = ObservableField("${data.results?.size} Items")
}