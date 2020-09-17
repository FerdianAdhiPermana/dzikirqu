package com.mayburger.dzikirqu.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class MainViewModel @ViewModelInject constructor(dataManager: DataManager,schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager,schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

    init {
        dataManager.language = "en"
    }
}