package com.mayburger.dzikirqu.ui.main

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class MainViewModel @ViewModelInject constructor(dataManager: DataManager,schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager,schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

    val selectedBottomNav = MutableLiveData(0)
    val selectedBottomNavTitle = ObservableField("Home")

    fun onClickBottomNav(position: Int) {
        selectedBottomNav.value = position
    }

}