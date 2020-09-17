package com.mayburger.dzikirqu.ui.base

import androidx.lifecycle.ViewModel
import com.mayburger.dzikirqu.util.rx.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


abstract class BaseItemViewModel(
) : ViewModel() {

    private val TAG = "BaseViewModel"
    //it's must be inject from dagger
    val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
            RxBus.getDefault().toObservables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ obj ->
                    onEvent(obj)
                }, { it.printStackTrace() })
        )
    }

    abstract fun onEvent(obj: Any)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
