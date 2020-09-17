package com.mayburger.dzikirqu.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class SplashViewModel @ViewModelInject constructor(dataManager: DataManager,schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager,schedulerProvider) {
    override fun onEvent(obj: Any) {

    }
}