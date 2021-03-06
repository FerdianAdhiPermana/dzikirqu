package com.mayburger.dzikirqu.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.databinding.ActivitySearchBinding
import com.mayburger.dzikirqu.model.events.KeywordDelayEvent
import com.mayburger.dzikirqu.model.events.KeywordEvent
import com.mayburger.dzikirqu.ui.adapters.TabPagerAdapter
import com.mayburger.dzikirqu.ui.base.BaseActivity
import com.mayburger.dzikirqu.ui.search.ayah.SearchAyahFragment
import com.mayburger.dzikirqu.ui.search.prayer.SearchPrayerFragment
import com.mayburger.dzikirqu.ui.search.surah.SearchSurahFragment
import com.mayburger.dzikirqu.util.StringProvider
import com.mayburger.dzikirqu.util.ext.showKeyboard
import com.mayburger.dzikirqu.util.rx.RxBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.main_tab_layout.view.*
import java.util.*


@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), SearchNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_search
    override val viewModel: SearchViewModel by viewModels()

    private var mTimer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.navigator = this
        search.showKeyboard()
        setUpNavigation()
        setUpSearch()
    }

    fun setUpSearch(){
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (mTimer != null) {
                    mTimer?.cancel()
                }
                RxBus.getDefault().send(KeywordEvent())
                mTimer = Timer()
                mTimer?.schedule(
                    object : TimerTask() {
                        override fun run() {
                            RxBus.getDefault().send(KeywordDelayEvent(p0.toString()))
                        }
                    }, 500
                )
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun setUpNavigation() {
        val tabTitles = arrayOf(
            StringProvider.getInstance().getString(LocaleConstants.BOOK),
            StringProvider.getInstance().getString(LocaleConstants.SURAH),
            StringProvider.getInstance().getString(LocaleConstants.AYAH),
        )
        pager.adapter =
            TabPagerAdapter(
                this,
                arrayListOf(SearchPrayerFragment(), SearchSurahFragment(), SearchAyahFragment())
            )
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.customView = getTabView(tabTitles[position])
            pager.setCurrentItem(tab.position, true)
        }.attach()
        pager.setCurrentItem(0, true)
    }

    fun getTabView(title: String): View? {
        val view: View = LayoutInflater.from(this).inflate(
            R.layout.search_tab_layout,
            null
        )
        view.title.text = title
        return view
    }

    override fun onClickSearch() {
        search.requestFocus()
        search.showKeyboard()
    }

    companion object {
        fun newIntent(context: Context){
            context.startActivity(Intent(context,SearchActivity::class.java))
        }
    }

}