package com.mayburger.dzikirqu.ui.surah.fragment

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemJuzViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemSurahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers


class SurahPageViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SurahPageNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

    val isLoaded = ObservableBoolean(false)
    val type = ObservableField(0)

    val _refreshSurahs = MutableLiveData(false)
    val surahs = _refreshSurahs.switchMap {
        liveData(Dispatchers.IO) {
            try {
                if (it) {
                    emit(dataManager.getSurah().map { ItemSurahViewModel(it) }.toList())
                    isLoaded.set(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val _refreshJuz = MutableLiveData(false)
    val juz = _refreshJuz.switchMap {
        liveData(Dispatchers.IO) {
            try {
                if (it) {
                    val juzList = ArrayList<ItemJuzViewModel>()
                    for (i in 1..30) {
                        val startAyah = dataManager.getAyahByJuz(i, 1)[0]
                        val startSurah = dataManager.getSurahById(startAyah.surahId)[0]
                        juzList.add(
                            ItemJuzViewModel(
                                i.toString(),
                                "Starts from: ${startSurah.name} verse ${startAyah.id}"
                            )
                        )
                    }
                    emit(juzList.toList())
                    isLoaded.set(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}