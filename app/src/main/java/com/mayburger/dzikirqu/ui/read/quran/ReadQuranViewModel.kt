package com.mayburger.dzikirqu.ui.read.quran

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemAyahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers.IO


class ReadQuranViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<ReadQuranNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

    val isLoaded = ObservableBoolean(false)
    val _surahId = MutableLiveData(-1)
    val ayahs = _surahId.switchMap {
        liveData(IO) {
            try {
                emit(dataManager.getAyahBySurahId(it).map{ ItemAyahViewModel(it) }.toList())
                isLoaded.set(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val surah = _surahId.switchMap{
        liveData(IO){
            try{
                emit(dataManager.getSurahById(it)[0])
            }catch (e:Exception){

            }
        }
    }

}