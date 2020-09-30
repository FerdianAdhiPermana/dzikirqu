package com.mayburger.dzikirqu.ui.search.surah

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.events.KeywordDelayEvent
import com.mayburger.dzikirqu.model.events.KeywordEvent
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemSurahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers


class SearchSurahViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SearchSurahNavigator>(dataManager, schedulerProvider) {

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
    val surah = searchQuery.switchMap {
        liveData(Dispatchers.IO) {
            try {
                if (it != "") {
                    emit(dataManager.getSurahByName(it).map { ItemSurahViewModel(it) }.toList())
                    isLoaded.set(true)
                } else {
                    emit(ArrayList<ItemSurahViewModel>())
                    isLoaded.set(true)
                }
            } catch (e: Exception) {
                isLoaded.set(true)
                navigator?.onError(e.message)
            }
        }
    }
    
}