package com.mayburger.dzikirqu.ui.read.quran.options

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class ReadQuranBSDViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<ReadQuranBSDNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

    fun onClickLastRead(){
        navigator?.onClickLastRead()
    }

}