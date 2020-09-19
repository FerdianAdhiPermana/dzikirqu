package com.mayburger.dzikirqu.ui.read

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class ReadViewModel @ViewModelInject constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ReadNavigator>(dataManager,schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

}