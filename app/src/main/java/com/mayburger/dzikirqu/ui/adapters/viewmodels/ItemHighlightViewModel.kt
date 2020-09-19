package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.HighlightDataModel

class ItemHighlightViewModel(val data: HighlightDataModel) {

    val title = ObservableField(data.title)
    var bookTitle = ObservableField(data.bookTitle)
    val desc = ObservableField("")

}