package com.mayburger.dzikirqu.util.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mayburger.dzikirqu.model.PrayerTime
import com.mayburger.dzikirqu.util.StringProvider
import com.mayburger.dzikirqu.util.rx.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object TextViewBinding {

    @BindingAdapter("maxLines")
    @JvmStatic
    fun setMaxlines(view: TextView, maxlines: Int) {
        view.post {
            if (view.text != null) {
                view.maxLines = maxlines
            }
            Int.MAX_VALUE
        }
    }

    @BindingAdapter("nameAbbreviation")
    @JvmStatic
    fun setNameAbbreviation(view: TextView, _name: String) {
        if (_name.split(" ").size > 1) {
            view.text = "${_name.split(" ")[0][0]}${_name.split(" ")[1][0]}"
        } else {
            view.text = "${_name.split(" ")[0][0]}"
        }
    }


    @BindingAdapter("text")
    @JvmStatic
    fun bindTextView(view: TextView, str: String) {
        setText(view, str)
//        CompositeDisposable().add(
//            RxBus.getDefault().toObservables()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ obj ->
//                    when (obj) {
//                        is PreferenceEvent -> {
//                            setText(view, str)
//                        }
//                    }
//                }, { it.printStackTrace() })
//        )
    }

    @BindingAdapter("textPrayerTime")
    @JvmStatic
    fun bindTextPrayerTime(view:TextView, prayer: PrayerTime){

    }

    fun setText(view: TextView, str: String) {
        view.text = StringProvider.getInstance().getString(str)
    }

}