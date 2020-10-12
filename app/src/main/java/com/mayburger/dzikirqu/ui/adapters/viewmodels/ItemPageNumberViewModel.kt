package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.ui.base.BaseHorizontalViewModel

class ItemPageNumberViewModel (val number: Int, val isSelected:Boolean): BaseHorizontalViewModel {

    val index = ObservableField((number).toString())

    override fun layoutId(): Int {
        return R.layout.item_page_number
    }

}