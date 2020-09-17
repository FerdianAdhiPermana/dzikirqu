package com.mayburger.dzikirqu.ui.main.book

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.PrayerTime
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.ui.main.home.HomeNavigator
import com.mayburger.dzikirqu.util.praytimes.PrayerTimeHelper
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class BookListViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

    val _bookType = MutableLiveData("")

    val book = _bookType.switchMap {
        liveData(IO) {
            try {
                if (it != "") {
                    val book = dataManager.getBookData(it)
                    emit(book.filter { it.data.language == dataManager.language })
                }
            } catch (e: Exception) {
                navigator?.onError(e.message)
            }
        }
    }

}