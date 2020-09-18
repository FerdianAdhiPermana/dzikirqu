package com.mayburger.dzikirqu.ui.main.home

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.PrayerTime
import com.mayburger.dzikirqu.model.events.UpdateTaskEvent
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.praytimes.PrayerTimeHelper
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HomeViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
        when (obj) {
            is UpdateTaskEvent -> {
                viewModelScope.launch {
                    dataManager.updateTask(obj.task)
                    _refreshTasks.postValue(true)
                }
            }
        }
    }

    val _refreshBooks = MutableLiveData(false)
    val books = _refreshBooks.switchMap {
        liveData(IO) {
            try {
                if (dataManager.getAllBooks().isNotEmpty()) {
                    emit(dataManager.getAllBooks().filter { it.language == dataManager.language }
                        .map { ItemBookViewModel(it) }.toList())
                }
                emit(dataManager.getBooks().filter { it.data.language == dataManager.language })
            } catch (e: Exception) {
                navigator?.onError(e.message)
            }
        }
    }

    val _refreshTasks = MutableLiveData(false)
    val tasks = _refreshTasks.switchMap {
        liveData(IO) {
            try {
                emit(dataManager.getTasks())
            } catch (e: java.lang.Exception) {
                navigator?.onError(e.message)
                println("EX ${e.message}")
            }
        }
    }

    val _updatePrayerTime = MutableLiveData(false)
    val prayerTime = _updatePrayerTime.switchMap {
        liveData(Main) {
            try {
//                emit(PrayerTimeHelper.getPrayerTimeFromHawk())
                emit(dataManager.getPrayerTime())
                buildPrayerTime()
            } catch (e: java.lang.Exception) {
                println("EXA ${e.message}")
            }
        }
    }

    var prayer = PrayerTime()
    var newtimer: CountDownTimer? = null
    val observer: Observer<PrayerTime> = Observer { prayer = it }
    var prayerTimeText = ObservableField("Loading..")
    var prayerUntilTime = ObservableField("Loading...")
    fun buildPrayerTime() {
        prayerTime.observeForever(observer)
        newtimer = object : CountDownTimer(864000000, 1000) {
            override fun onFinish() {}

            @SuppressLint("SimpleDateFormat")
            override fun onTick(millisUntilFinished: Long) {
                try {
                    val cal = Calendar.getInstance()
                    val date = cal.time
                    val dateFormat = SimpleDateFormat("HH:mm")
                    val now = dateFormat.format(date)
                    if (prayer.address != "Loading...") {
                        prayer.let { PrayerTimeHelper.buildPrayerTime(prayerTimeText, now, it) }
                        prayer.let { PrayerTimeHelper.buildPrayerUntil(prayerUntilTime, now, it) }
                    }
                } catch (e: Exception) {
                }
            }
        }
        newtimer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        if (newtimer != null) {
            (newtimer as CountDownTimer).cancel()
        }
    }

//    private var _prayerTime:MutableLiveData<PrayerTime> = MutableLiveData()
//    val prayerTime : LiveData<PrayerTime> = _prayerTime
//
//    fun getPrayers(context: Context){
//        _prayerTime.postValue(PrayerTimeHelper.getPrayerTime(context).value)
//    }
}