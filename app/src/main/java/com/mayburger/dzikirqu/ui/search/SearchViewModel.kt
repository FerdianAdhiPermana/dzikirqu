package com.mayburger.dzikirqu.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.ui.main.home.HomeNavigator
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class SearchViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SearchNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

}