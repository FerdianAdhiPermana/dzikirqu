package com.mayburger.dzikirqu.ui.search.prayer

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.events.KeywordDelayEvent
import com.mayburger.dzikirqu.model.events.KeywordEvent
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemPrayerViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers


class SearchPrayerViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SearchPrayerNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
        when (obj) {
            is KeywordDelayEvent -> {
                searchQuery.postValue(obj.query)
            }
            is KeywordEvent ->{
                isLoaded.set(false)
            }
        }
    }

    val isLoaded = ObservableBoolean(false)
    val searchQuery = MutableLiveData("")
    val prayer = searchQuery.switchMap {
        liveData(Dispatchers.IO) {
            try {
                if (it != "") {
                    emit(dataManager.getPrayerByTitle(it).map { ItemPrayerViewModel(it) }.toList())
                    isLoaded.set(true)
                } else {
                    emit(ArrayList<ItemPrayerViewModel>())
                    isLoaded.set(true)
                }
            } catch (e: Exception) {
                isLoaded.set(true)
                navigator?.onError(e.message)
            }
        }
    }

}