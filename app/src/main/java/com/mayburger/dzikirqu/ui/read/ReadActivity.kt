package com.mayburger.dzikirqu.ui.read

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ActivityReadBinding
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.base.BaseActivity
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.util.ext.hide
import com.mayburger.dzikirqu.util.ext.isShowing
import com.mayburger.dzikirqu.util.ext.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_read.*

@AndroidEntryPoint
class ReadActivity : BaseActivity<ActivityReadBinding, ReadViewModel>(),ReadNavigator{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_read
    override val viewModel: ReadViewModel by viewModels()

    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    companion object {
        const val EXTRA_PRAYER = "extra_prayer"
        const val EXTRA_BOOK_TITLE = "extra_book_title"
        const val EXTRA_SURAH_ID = "extra_surah_id"
        const val EXTRA_JUZ_ID = "extra_juz_id"
        const val EXTRA_VERSE_ID = "extra_verse_id"
        fun start(
            context: Context,
            prayer: PrayerDataModel? = null,
            bookTitle: String? = null,
            surahId: Int? = null,
            verseId:Int?= null,
            juzId:Int?=null
        ) {
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra(EXTRA_PRAYER, prayer)
            intent.putExtra(EXTRA_BOOK_TITLE, bookTitle)
            intent.putExtra(EXTRA_SURAH_ID, surahId)
            intent.putExtra(EXTRA_VERSE_ID,verseId)
            intent.putExtra(EXTRA_JUZ_ID,juzId)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.navigator = this
        val navigator: Int
        val surahId = intent?.getIntExtra(EXTRA_SURAH_ID, -1)
        val juzId = intent?.getIntExtra(EXTRA_JUZ_ID, -1)
        navigator = when {
            surahId != -1 -> {
                R.navigation.nav_read_surah
            }
            juzId != -1 -> {
                R.navigation.nav_read_juz
            }
            else -> {
                R.navigation.nav_read_prayer
            }
        }
        val host = NavHostFragment.create(navigator)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, host)
            .setPrimaryNavigationFragment(host)
            .commit()
        sheetBehavior = BottomSheetBehavior.from(sheet)
        buildBottomSheet(sheetBehavior)
    }

    override fun onBackPressed() {
        if (sheetBehavior.isShowing()) {
            sheetBehavior.hide()
        } else {
            super.onBackPressed()
        }
    }

    override fun dismissBottomSheet() {
        sheetBehavior.hide()
    }

    fun buildBottomSheet(sheetBehavior: BottomSheetBehavior<*>) {
        sheetBehavior.isHideable = true
        sheetBehavior.hide()
        sheetBehavior.skipCollapsed = true
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

    override fun showBottomSheet(fragment: BaseFragment<*, *>, tag: String) {
        super.showBottomSheet(fragment, tag)
        sheetBehavior.show()
        sheetBehavior.hide()
        sheetBehavior.show()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.sheet_container, fragment, tag)
        transaction.commit()
    }
}