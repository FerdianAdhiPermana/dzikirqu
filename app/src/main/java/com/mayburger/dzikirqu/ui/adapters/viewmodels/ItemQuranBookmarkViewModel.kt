package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.util.StringProvider

class ItemQuranBookmarkViewModel(val data: AyahDataModel) {
    val verse = ObservableField(
        String.format(
            StringProvider.getInstance().getString(LocaleConstants.VERSE_NO_N), data.id.toString()
        )
    )
}