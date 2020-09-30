package com.mayburger.dzikirqu.ui.search.prayer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentSearchPrayerBinding
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.adapters.PrayerAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_prayer.*
import javax.inject.Inject


@AndroidEntryPoint
class SearchPrayerFragment : BaseFragment<FragmentSearchPrayerBinding, SearchPrayerViewModel>(),
    SearchPrayerNavigator,PrayerAdapter.Callback{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_search_prayer
    override val viewModel: SearchPrayerViewModel by viewModels()

    @Inject
    lateinit var prayerAdapter: PrayerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        setUpAdapter()
    }
    fun setUpAdapter() {
        rvPrayer.adapter = prayerAdapter
        prayerAdapter.setListener(this)
    }

    override fun onSelectedItem(prayer: PrayerDataModel) {

    }
}