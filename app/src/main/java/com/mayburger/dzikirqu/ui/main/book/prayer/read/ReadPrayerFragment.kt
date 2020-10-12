package com.mayburger.dzikirqu.ui.main.book.prayer.read

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.LocaleConstants
import com.mayburger.dzikirqu.databinding.FragmentReadPrayerBinding
import com.mayburger.dzikirqu.ui.adapters.PrayerAdapter
import com.mayburger.dzikirqu.ui.adapters.viewmodels.PagePrayerViewModel
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import com.mayburger.dzikirqu.util.StringProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_read_prayer.*
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
    lateinit var prayerAdapter: PrayerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigator = this
        viewModel.prayer.set(requireActivity().intent.getParcelableExtra(ReadActivity.EXTRA_PRAYER))
        viewModel.bookTitle.set(requireActivity().intent.getStringExtra(ReadActivity.EXTRA_BOOK_TITLE))
        setPosition(1)
        prayerAdapter.isReadMode(true)
        prayerAdapter.addReadItems(viewModel.prayer.get()?.data?.toCollection(arrayListOf())?.map {
            PagePrayerViewModel(it)
        }?.toCollection(arrayListOf()) ?: ArrayList())
        pager.adapter = prayerAdapter
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setPosition(position + 1)
            }
        })
    }

    override fun mutatePosition(modifier: Int) {
        pager.currentItem = pager.currentItem + modifier
        View.SCALE_X
        footer.animate()
            .z(30f)
            .start()
    }

    fun setPosition(position: Int) {
        val audio = viewModel.prayer.get()?.data?.get(position - 1)?.audio
        viewModel.showAudio.set((audio != null && audio != ""))
        viewModel.position.set(position)
        viewModel.indexString.set(
            String.format(
                StringProvider.getInstance().getString(LocaleConstants.OF),
                position.toString(),
                viewModel.prayer.get()?.data?.size.toString()
            )
        )
    }
}