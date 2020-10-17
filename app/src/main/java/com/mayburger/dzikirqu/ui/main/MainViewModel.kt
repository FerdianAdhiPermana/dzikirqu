package com.mayburger.dzikirqu.ui.main

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mayburger.dzikirqu.constants.Constants
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.model.AyahJsonModel
import com.mayburger.dzikirqu.model.SurahJsonModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.FileUtils
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import java.util.*


class MainViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }

    fun onClickSearch() {
        navigator?.onClickSearch()
    }

    suspend fun setUpQuran(context: Context) {
        if (dataManager.getSurah().isEmpty()) {
            dataManager.setSurah(
                Gson().fromJson(
                    FileUtils.getJsonStringFromAssets(
                        context,
                        "json/lang/en.json"
                    ),
                    object : TypeToken<ArrayList<SurahJsonModel>>() {}.type
                ), "en"
            )
            dataManager.setSurah(
                Gson().fromJson(
                    FileUtils.getJsonStringFromAssets(
                        context,
                        "json/lang/id.json"
                    ),
                    object : TypeToken<ArrayList<SurahJsonModel>>() {}.type
                ), "id"
            )
        }

        if (dataManager.getAllAyahs().size != Constants.QURAN_AYAH_SIZE) {
            dataManager.deleteAllAyahs()
            for (i in dataManager.getSurah()) {
                val ayah = Gson().fromJson<ArrayList<AyahJsonModel>>(
                    FileUtils.getJsonStringFromAssets(
                        context,
                        "json/quran/${i.name}.json"
                    ), object : TypeToken<ArrayList<AyahJsonModel>>() {}.type
                )
                dataManager.insertAyah(ayah)
            }
        }
    }
}