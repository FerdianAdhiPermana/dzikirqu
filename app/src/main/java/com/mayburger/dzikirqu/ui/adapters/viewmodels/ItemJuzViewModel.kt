package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField

class ItemJuzViewModel (val number:String, val startFrom:String){
    val juzNumber = ObservableField("Juz ${number}")
}