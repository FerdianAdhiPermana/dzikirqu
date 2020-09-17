package com.mayburger.dzikirqu.ui.main.book

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.ui.base.BaseViewModel
import com.mayburger.dzikirqu.ui.main.home.HomeNavigator
import com.mayburger.dzikirqu.util.rx.SchedulerProvider


class BookListViewModel @ViewModelInject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {
    override fun onEvent(obj: Any) {

    }

    val _bookType = MutableLiveData("")
//
//    val book = _bookType.switchMap {
//        liveData(IO) {
//            try {
//                if (it != "") {
//                    val book = dataManager.getBookData(it)
//                    emit(book.filter { it.data.language == dataManager.language })
//                }
//            } catch (e: Exception) {
//                navigator?.onError(e.message)
//            }
//        }
//    }

}