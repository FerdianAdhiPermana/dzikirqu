package com.mayburger.dzikirqu.ui.read

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ActivityReadBinding
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadActivity : BaseActivity<ActivityReadBinding, ReadViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_read
    override val viewModel: ReadViewModel by viewModels()

    companion object {
        const val EXTRA_PRAYER = "extra_prayer"
        const val EXTRA_BOOK_TITLE = "extra_book_title"
        const val EXTRA_SURAH_ID = "extra_surah_id"
        fun start(context: Context, prayer: PrayerDataModel? = null,bookTitle:String?=null,surahId: Int? = null) {
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra(EXTRA_PRAYER, prayer)
            intent.putExtra(EXTRA_BOOK_TITLE, bookTitle)
            intent.putExtra(EXTRA_SURAH_ID, surahId)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        var navigator = 0

        val surahId = intent?.getIntExtra(EXTRA_SURAH_ID, -1)
        if(surahId != -1){
            navigator = R.navigation.nav_read_quran
        } else{
            navigator = R.navigation.nav_read_prayer
        }
        val host = NavHostFragment.create(navigator)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, host)
            .setPrimaryNavigationFragment(host)
            .commit()
    }
}