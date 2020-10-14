package com.mayburger.dzikirqu.ui.main.book.prayer.read

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class ReadPrayerViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<ReadPrayerNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

    val prayer = ObservableField<PrayerDataModel>()
    val bookTitle = ObservableField<String>()
    val indexString = ObservableField<String>()
    val position = ObservableField(0)
    val showAudio = ObservableField(false)
    val showNavigation = ObservableField(false)

    fun next(){
        navigator?.mutatePosition(1)
    }
    fun previous(){
        navigator?.mutatePosition(-1)
    }
}