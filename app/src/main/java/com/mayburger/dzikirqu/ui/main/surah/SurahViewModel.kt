package com.mayburger.dzikirqu.ui.main.surah

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemSurahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers


class SurahViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SurahNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

    val isLoaded = ObservableBoolean(false)

    val _refreshSurahs = MutableLiveData(false)
    val surahs = _refreshSurahs.switchMap {
        liveData(Dispatchers.IO) {
            try {
                emit(dataManager.getSurah().map{ ItemSurahViewModel(it) }.toList())
                isLoaded.set(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }





}