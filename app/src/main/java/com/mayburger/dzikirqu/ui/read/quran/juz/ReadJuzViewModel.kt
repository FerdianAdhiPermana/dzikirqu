package com.mayburger.dzikirqu.ui.read.quran.juz

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.BismillahObject
import com.mayburger.dzikirqu.model.SurahObject
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemAyahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers.IO


class ReadJuzViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<ReadJuzNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

    val isLoaded = ObservableBoolean(false)
    val _juzId = MutableLiveData(-1)

    val title = ObservableField("")
    val subtitle = ObservableField("")

    val ayah = _juzId.switchMap {
        liveData(IO) {
            try {
                if (it != -1) {
                    val any = ArrayList<Any>()
                    dataManager.getAyahByJuz(it,9999).map { ItemAyahViewModel(it) }.toList()?.map{
                        if(it.data.id == 1){
                            any.add(SurahObject(dataManager.getSurahById(it.data.surahId)[0]))
                        }
                        if(it.data.use_bismillah == true){
                            any.add(BismillahObject())
                        }
                        any.add(it)
                    }
                    emit(any.toList())
                    isLoaded.set(true)
                    navigator?.onLoadQuran()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}