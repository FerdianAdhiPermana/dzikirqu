package com.mayburger.dzikirqu.ui.search.ayah

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.events.KeywordDelayEvent
import com.mayburger.dzikirqu.model.events.KeywordEvent
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemSearchAyahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers


class SearchAyahViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SearchAyahNavigator>(dataManager, schedulerProvider) {

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
    val ayah = searchQuery.switchMap {query->
        liveData(Dispatchers.IO) {
            try {
                if (query != "") {
                    val ayah = dataManager.getAyahByTranslation(query).map { ItemSearchAyahViewModel(it,query) }.toList()
                    if(ayah.isEmpty()){
                        showDescription.set(true)
                        description.set("Cannot find what you're looking for")
                    }
                    emit(ayah)
                    showLoading.set(false)
                } else {
                    emit(ArrayList<ItemSearchAyahViewModel>())
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