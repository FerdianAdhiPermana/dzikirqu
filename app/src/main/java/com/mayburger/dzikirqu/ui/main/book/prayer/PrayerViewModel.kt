package com.mayburger.dzikirqu.ui.main.book.prayer

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

    val _bookId = MutableLiveData(-1)
    val bookTitle = MutableLiveData("")
    val bookDesc = MutableLiveData("")

    val prayer = _bookId.switchMap { bookId ->
        liveData(IO) {
            try {
                if (bookId != -1) {
                    println("It was called")
                    emit(dataManager.getPrayerByBook(bookId).map{ ItemPrayerViewModel(it) }.toList())
                    println("It was ended")
                }
            } catch (e: Exception) {
                navigator?.onError(e.message)
            }
        }
    }
}