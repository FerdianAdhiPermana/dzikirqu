package com.mayburger.dzikirqu.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.databinding.FragmentSearchBinding
import com.mayburger.dzikirqu.model.events.KeywordEvent
import com.mayburger.dzikirqu.ui.adapters.MainPagerAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.search.ayah.SearchAyahFragment
import com.mayburger.dzikirqu.ui.search.prayer.SearchPrayerFragment
import com.mayburger.dzikirqu.ui.search.surah.SearchSurahFragment
import com.mayburger.dzikirqu.util.StringProvider
import com.mayburger.dzikirqu.util.ext.showKeyboard
import com.mayburger.dzikirqu.util.rx.RxBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.main_tab_layout.view.*
import java.util.*


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(), SearchNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModels()

    private var mTimer: Timer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
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
                mTimer = Timer()
                mTimer?.schedule(
                    object : TimerTask() {
                        override fun run() {
                            RxBus.getDefault().send(KeywordEvent(p0.toString()))
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
            MainPagerAdapter(
                requireAppActivity(),
                arrayListOf(SearchPrayerFragment(), SearchSurahFragment(), SearchAyahFragment())
            )
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.customView = getTabView(tabTitles[position])
            pager.setCurrentItem(tab.position, true)
        }.attach()
        pager.setCurrentItem(0, true)
    }

    fun getTabView(title: String): View? {
        val view: View = LayoutInflater.from(requireActivity()).inflate(
            R.layout.search_tab_layout,
            null
        )
        view.title.text = title
        return view
    }

    override fun onClickSearch() {
        search.requestFocus()
    }

    companion object {
        fun remove(activity: AppCompatActivity) {
            activity.supportFragmentManager.findFragmentByTag(SearchFragment().javaClass.name)?.let {
                activity.supportFragmentManager.beginTransaction().remove(it).commit()
            }
        }
    }

}