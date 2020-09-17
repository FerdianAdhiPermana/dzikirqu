package com.mayburger.dzikirqu.ui.main.book

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemPrayerViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers.IO


class PrayerViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<PrayerNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

    val _bookId = MutableLiveData("")

    val prayer = _bookId.switchMap { bookId ->
        liveData(IO) {
            try {
                if (bookId != "") {
                    emit(dataManager.getPrayerByBookId(bookId).map{
                        ItemPrayerViewModel(it)
                    }.toList())
                }
            } catch (e: Exception) {
                navigator?.onError(e.message)
            }
        }
    }
}