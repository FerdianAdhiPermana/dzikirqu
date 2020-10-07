package com.mayburger.dzikirqu.ui.praytime

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.PrayerTime
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.praytimes.PrayerTimeHelper
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*


class PrayTimeViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<PrayTimeNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }


    val prayerTime = liveData(Dispatchers.Main) {
        try {
            val hawk = PrayerTimeHelper.getPrayerTimeFromHawk()
            emit(hawk)
        } catch (e: java.lang.Exception) {
            println("EXACT ${e.message}")
        }
    }

    var newtimer: CountDownTimer? = null
    var prayerTimeText = ObservableField("Loading..")
    var prayerUntilTime = ObservableField("Loading...")
    fun buildPrayerTime(prayer: PrayerTime) {
        newtimer?.cancel()
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

}