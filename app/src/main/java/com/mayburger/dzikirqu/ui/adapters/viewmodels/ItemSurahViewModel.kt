package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.util.StringProvider

class ItemSurahViewModel (val data: SurahDataModel){



    fun getRevelation():ObservableField<String>{
        return if(data.revelation == MAKKIYYAH){
            ObservableField("Makkah")
        } else{
            ObservableField("Madinah")
        }
    }

    val verses = ObservableField("${data.verses} ${StringProvider.getInstance().getString(LocaleConstants.AYAT)}")
    val index = ObservableField("${data.id}")

    companion object{
        val MAKKIYYAH = "makkah"
        val MADANIYYAH = "madinah"
    }
}