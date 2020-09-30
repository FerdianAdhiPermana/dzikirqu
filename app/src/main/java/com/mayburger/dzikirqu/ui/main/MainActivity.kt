package com.mayburger.dzikirqu.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.databinding.ActivityMainBinding
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.adapters.MainPagerAdapter
import com.mayburger.dzikirqu.ui.base.BaseActivity
import com.mayburger.dzikirqu.ui.main.book.BookFragment
import com.mayburger.dzikirqu.ui.main.home.HomeFragment
import com.mayburger.dzikirqu.ui.main.surah.SurahFragment
import com.mayburger.dzikirqu.ui.search.SearchFragment
import com.mayburger.dzikirqu.util.StringProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_tab_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

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
        viewModel.navigator = this
        buildLocationPermission()
        viewModel.dataManager.language = "id"
        CoroutineScope(IO).launch {
            viewModel.setUpQuran(this@MainActivity)
        }
    }

    override fun onClickSearch() {
        val search = SearchFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, search, search.javaClass.name).commit()
    }

    override fun onBackPressed() {
        if (viewModel.showSearch.get()) {
            SearchFragment.remove(this)
            viewModel.showSearch.set(false)
        } else {
            finish()
        }
    }

    val pagerChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.selectedTab.value = position
        }
    }

    fun setUpNavigation() {
        val tabTitles = arrayOf(
            StringProvider.getInstance().getString(LocaleConstants.BOOK),
            StringProvider.getInstance().getString(LocaleConstants.HOME),
            StringProvider.getInstance().getString(LocaleConstants.QURAN),
        )
        val tabImages =
            arrayOf(R.drawable.ic_book_white, R.drawable.ic_home_white, R.drawable.ic_quran_white)
        pager.adapter =
            MainPagerAdapter(this, arrayListOf(BookFragment(), HomeFragment(), SurahFragment()))
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = tabTitles[position]
            tab.customView = getTabView(tabTitles[position], tabImages[position])
            pager.setCurrentItem(tab.position, true)
        }.attach()
        pager.setCurrentItem(1, true)
        pager.registerOnPageChangeCallback(pagerChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        pager.unregisterOnPageChangeCallback(pagerChangeCallback)
    }

    fun getTabView(title: String, image: Int): View? {
        val view: View = LayoutInflater.from(this).inflate(R.layout.main_tab_layout, null)
        view.title.text = title
        view.image.setImageResource(image)
        return view
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