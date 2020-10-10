package com.mayburger.dzikirqu.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ActivityMainBinding
import com.mayburger.dzikirqu.ui.base.BaseActivity
import com.mayburger.dzikirqu.ui.main.book.prayer.PrayerFragment
import com.mayburger.dzikirqu.ui.search.SearchActivity
import com.mayburger.dzikirqu.util.ext.collapse
import com.mayburger.dzikirqu.util.ext.hide
import com.mayburger.dzikirqu.util.ext.isShowing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.navigator = this
        buildLocationPermission()
        CoroutineScope(IO).launch {
            viewModel.setUpQuran(this@MainActivity)
        }
        buildBottomSheet()
    }

    override fun onClickSearch() {
        SearchActivity.newIntent(this)
    }

    override fun showBottomSheet(fragment: Fragment) {
        super.showBottomSheet(fragment)
        sheetBehavior.collapse()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.sheet_container, fragment, PrayerFragment.TAG)
        transaction.commit()
    }

    fun buildBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(sheet)
        sheetBehavior.isHideable = true
        sheetBehavior.hide()
        alpha.setOnClickListener {
            sheetBehavior.hide()
        }
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                alpha.visibility = if (slideOffset + 1 > 0) View.VISIBLE else View.GONE
                alpha.alpha = slideOffset + 1
            }
        })
    }

    fun setUpNavigation() {
        val host = NavHostFragment.create(R.navigation.nav_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, host)
            .setPrimaryNavigationFragment(host)
            .commit()
    }

    override fun onBackPressed() {
        if (sheetBehavior.isShowing()) {
            sheetBehavior.hide()
        } else {
            super.onBackPressed()
        }
    }

    private val LOCATION_PERMISSION_CODE = 93

    @SuppressLint("MissingPermission")
    private fun buildLocationPermission() {
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            setUpNavigation()
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
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            LOCATION_PERMISSION_CODE
        )
    }

    fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                setUpNavigation()
            }
        }
    }

}