package com.mayburger.dzikirqu.ui.main.book.prayer.read

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentReadPrayerBinding
import com.mayburger.dzikirqu.ui.adapters.PrayerPagerAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_prayer.*
import javax.inject.Inject

@AndroidEntryPoint
class ReadPrayerFragment : BaseFragment<FragmentReadPrayerBinding, ReadPrayerViewModel>(),
    ReadPrayerNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_read_prayer
    override val viewModel: ReadPrayerViewModel by viewModels()

    @Inject
    lateinit var prayerPagerAdapter: PrayerPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.prayer.set(requireActivity().intent.getParcelableExtra<PrayerDataModel>(ReadActivity.EXTRA_PRAYER)?.data?.toCollection(arrayListOf()))
        rvPrayer.adapter = prayerPagerAdapter
    }
}