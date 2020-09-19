package com.mayburger.dzikirqu.ui.main.book

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers


class BookViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<BookNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {
    }


    val _refreshBooks = MutableLiveData(false)
    val books = _refreshBooks.switchMap {
        liveData(Dispatchers.IO) {
            try {
                if (dataManager.getAllBooks().isNotEmpty()) {
                    emit(dataManager.getAllBooks().filter { it.language == dataManager.language }
                        .map { ItemBookViewModel(it) }.toList())
                }
                emit(dataManager.getBooks().filter { it.data.language == dataManager.language })
            } catch (e: Exception) {
                println("ES ${e.message}")
                navigator?.onError(e.message)
            }
        }
    }


}