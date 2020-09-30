package com.mayburger.dzikirqu.ui.search.ayah

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class SearchAyahViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SearchAyahNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

}