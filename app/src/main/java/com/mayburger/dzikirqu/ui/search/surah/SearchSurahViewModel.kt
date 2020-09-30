package com.mayburger.dzikirqu.ui.search.surah

import androidx.hilt.lifecycle.ViewModelInject
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class SearchSurahViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SearchSurahNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

}