package com.mayburger.dzikirqu.ui.praytime

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.PrayerTime
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.StringProvider
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

    val currentTime = ObservableField("")

    var newtimer: CountDownTimer? = null
    var prayerTimeText = ObservableField("Loading..")
    var prayerNext = ObservableField("")
    var prayerUntilTime = ObservableField("Loading...")

    var untilFajr = ObservableField("")
    var untilDhuhr = ObservableField("")
    var untilAsr = ObservableField("")
    var untilMaghrib = ObservableField("")
    var untilIsya = ObservableField("")

    fun buildPrayers(prayer:PrayerTime){
        untilFajr.set(PrayerTimeHelper.countTimeLight(prayer.fajr?:"",
            StringProvider.getInstance().getString(LocaleConstants.FAJR)))
        untilDhuhr.set(PrayerTimeHelper.countTimeLight(prayer.dhuhr?:"",
            StringProvider.getInstance().getString(LocaleConstants.DHUHR)))
        untilAsr.set(PrayerTimeHelper.countTimeLight(prayer.asr?:"",
            StringProvider.getInstance().getString(LocaleConstants.ASR)))
        untilMaghrib.set(PrayerTimeHelper.countTimeLight(prayer.maghrib?:"",
            StringProvider.getInstance().getString(LocaleConstants.MAGHRIB)))
        untilIsya.set(PrayerTimeHelper.countTimeLight(prayer.isya?:"",
            StringProvider.getInstance().getString(LocaleConstants.ISYA)))
    }

    fun backFragment(){
        navigator?.onClickBack()
    }

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
                        prayerNext.set(prayer.let { PrayerTimeHelper.buildPrayerUntil(prayerUntilTime, now, it) })
                        currentTime.set(SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time))
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