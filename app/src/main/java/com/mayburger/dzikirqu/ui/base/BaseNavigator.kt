package com.mayburger.dzikirqu.ui.base

import android.content.ContentResolver
import androidx.fragment.app.Fragment

interface BaseNavigator{

    fun showLoading()

    fun hideLoading()

    fun finishActivity()

    fun onError(message: String?)

    fun showSnackBar(message:String)

    fun getContentResolver(): ContentResolver?

    fun showBottomSheet(fragment:Fragment)

}