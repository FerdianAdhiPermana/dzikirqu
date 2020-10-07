package com.mayburger.dzikirqu.ui.adapters.viewmodels

import android.text.Html
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.AyahDataModel

class ItemSearchAyahViewModel (val data: AyahDataModel, val query:String){

    val isIndexOdd = ObservableBoolean(false)
    val index = ObservableField(data.id.toString())
    val isLastIndex = ObservableBoolean(false)

    val translation = ObservableField(Html.fromHtml(data.translation.toLowerCase().replace(query.toLowerCase(),"<font color='red'>$query</font>")))

}