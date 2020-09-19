package com.mayburger.dzikirqu.ui.main.quran

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class QuranViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<QuranNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }
}