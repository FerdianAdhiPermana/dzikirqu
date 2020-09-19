package com.mayburger.dzikirqu.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.databinding.ActivityMainBinding
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseActivity
import com.mayburger.dzikirqu.ui.main.book.BookFragment
import com.mayburger.dzikirqu.ui.main.home.HomeFragment
import com.mayburger.dzikirqu.ui.main.quran.QuranFragment
import com.mayburger.dzikirqu.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
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
//        val books:ArrayList<BookDataModel> = Gson().fromJson(getJsonStringFromRaw(R.raw.dzikir)
//        ,object:TypeToken<ArrayList<BookDataModel>>(){}.type)
//        books.map{
//            Firebase.firestore.collection("books").add(it)
//        }
    }
    fun getJsonStringFromRaw(rawInt: Int): String {
        val `is`: InputStream = resources.openRawResource(rawInt)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        `is`.use { `is` ->
            val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }
    
    fun setUpNavigation(){
        viewModel.selectedBottomNav.observe(this, {
            when (it) {
                0 -> {
                    loadFragment(HomeFragment())
                    viewModel.selectedBottomNavTitle.set(LocaleConstants.HOME)
                }
                1 -> {
                    loadFragment(BookFragment())
                    viewModel.selectedBottomNavTitle.set(LocaleConstants.BOOK)
                }
                2 -> {
                    loadFragment(QuranFragment())
                    viewModel.selectedBottomNavTitle.set(LocaleConstants.QURAN)
                }
            }
        })
    }



    fun loadFragment(fragment: Fragment) {
        ActivityUtil.loadFragment(
            R.id.container,
            supportFragmentManager,
            fragment
        )
    }

    override fun onBackPressed() {
        finish()
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