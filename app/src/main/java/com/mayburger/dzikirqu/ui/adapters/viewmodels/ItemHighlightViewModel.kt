package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.model.HighlightDataModel

class ItemHighlightViewModel(val data: HighlightDataModel) {
    fun getImageResource():ObservableField<Int>{
        return when(data.type){
            0->{
                ObservableField(R.drawable.ic_book_white)
            } else->{
                ObservableField(R.drawable.ic_quran_white)
            }
        }
    }

    val showClose = ObservableField(false)
}