package com.mayburger.dzikirqu.ui.main.book.prayer.read

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class ReadPrayerViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<ReadPrayerNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {

    }
}