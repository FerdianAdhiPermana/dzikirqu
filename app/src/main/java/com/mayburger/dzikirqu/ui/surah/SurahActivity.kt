package com.mayburger.dzikirqu.ui.surah

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.SurahTypeConstants
import com.mayburger.dzikirqu.databinding.ActivitySurahBinding
import com.mayburger.dzikirqu.ui.adapters.TabPagerAdapter
import com.mayburger.dzikirqu.ui.base.BaseActivity
import com.mayburger.dzikirqu.ui.surah.fragment.SurahPageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_surah.*
import kotlinx.android.synthetic.main.main_tab_layout.view.*


@AndroidEntryPoint
class SurahActivity : BaseActivity<ActivitySurahBinding, SurahViewModel>(), SurahNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_surah
    override val viewModel: SurahViewModel by viewModels()

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SurahActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.navigator = this

        val adapter = TabPagerAdapter(
            this,
            arrayListOf(
                SurahPageFragment.newInstance(SurahTypeConstants.TYPE_SURAH),
                SurahPageFragment.newInstance(SurahTypeConstants.TYPE_JUZ),
                SurahPageFragment.newInstance(SurahTypeConstants.TYPE_BOOKMARK)
            )
        )
        pager.adapter = adapter
        TabLayoutMediator(tab, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.customView = getTabLayout("Surah")
                }
                1 -> {
                    tab.customView = getTabLayout("Juz")
                }
                2 -> {
                    tab.customView = getTabLayout("Bookmarks")
                }
            }
        }.attach()
    }

    fun getTabLayout(title:String): View {
        val tab = LayoutInflater.from(this).inflate(R.layout.main_tab_layout,null,false)
        tab.title.text = title
        return tab
    }
}