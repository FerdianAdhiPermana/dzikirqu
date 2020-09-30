package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.AyahDataModel

class ItemQuranViewModel (val data: AyahDataModel){

    val isIndexOdd = ObservableBoolean(false)
    val index = ObservableField(data.id.toString())
    val isLastIndex = ObservableBoolean(false)
}