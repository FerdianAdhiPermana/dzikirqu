package com.mayburger.dzikirqu.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ActivityMainBinding
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        buildLocationPermission()
    }

    fun buildFragments(){
        val host = NavHostFragment.create(R.navigation.nav_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, host)
            .setPrimaryNavigationFragment(host)
            .commit()
    }


    private val LOCATION_PERMISSION_CODE = 93

    @SuppressLint("MissingPermission")
    private fun buildLocationPermission() {
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            buildFragments()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_CODE
        )
    }

    fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun showRequestPermissionsInfoAlertDialog(makeSystemRequest: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Permission") // Your own title
        builder.setMessage("This app require the device's location in order to get accurate prayer time") // Your own message

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            if (makeSystemRequest) {
                requestLocationPermission()
            }
        }

        builder.setCancelable(false)
        builder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showRequestPermissionsInfoAlertDialog(true)
            } else {
                buildFragments()
            }
        }
    }
}