package com.mayburger.dzikirqu.ui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.facebook.stetho.Stetho
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.util.ShakeDetector
import com.mayburger.dzikirqu.util.ext.ViewUtils


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseFragment.Callback,
    BaseNavigator {

    lateinit var viewDataBinding: T
        private set

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int
    private var pDialog: ProgressDialog? = null

    abstract val viewModel: V


    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pDialog = ViewUtils.getProgressDialog(this, "Please Wait")
        performDataBinding()
        FirebaseApp.initializeApp(this)
        Firebase.initialize(this)
        Stetho.initializeWithDefaults(this)
    }

    fun initShakeToOpenRestaurant(runnable: Runnable) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val shakeDetector = ShakeDetector {
            runnable.run()
        }
        shakeDetector.start(sensorManager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun hideLoading() {
        if (pDialog?.isShowing == true) pDialog?.dismiss()
    }

    override fun showLoading() {
        if (pDialog?.isShowing == false) pDialog?.show()
    }

    override fun onError(message: String?) {
        message?.let { showSnackBar(it) }
    }

    override fun showSnackBar(message: String) {
        val snackBar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val sbView: View = snackBar.view
        val textView: TextView = sbView.findViewById(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        snackBar.show()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        //this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding.apply {
            setVariable(bindingVariable, viewModel)
            executePendingBindings()
        }
    }


    override fun finishActivity() {
        finish()
    }

    override fun getContentResolver(): ContentResolver = super.getContentResolver()
}

