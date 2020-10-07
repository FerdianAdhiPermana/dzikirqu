package com.mayburger.dzikirqu.ui.search.prayer

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
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
                showLoading.set(true)
                showDescription.set(false)
            }
        }
    }

    val description = ObservableField("Search for something!")

    val showLoading = ObservableBoolean(false)
    val showDescription = ObservableBoolean(true)
    val searchQuery = MutableLiveData("")
    val prayer = searchQuery.switchMap {
        liveData(Dispatchers.IO) {
            showDescription.set(false)
            try {
                if (it != "") {
                    val prayer = dataManager.getPrayerByTitle(it).map { ItemPrayerViewModel(it) }.toList()
                    if(prayer.isEmpty()){
                        showDescription.set(true)
                        description.set("Cannot find what you're looking for")
                    }
                    emit(prayer)
                    showLoading.set(false)
                } else {
                    emit(ArrayList<ItemPrayerViewModel>())
                    showLoading.set(false)
                    showDescription.set(true)
                    description.set("Search for something!")
                }
            } catch (e: Exception) {
                showLoading.set(false)
                navigator?.onError(e.message)
            }
        }
    }

}