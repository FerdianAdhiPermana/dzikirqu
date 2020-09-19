package com.mayburger.dzikirqu.ui.main.book.prayer.read

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentReadPrayerBinding
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadPrayerFragment : BaseFragment<FragmentReadPrayerBinding, ReadPrayerViewModel>(),
    ReadPrayerNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_read_prayer
    override val viewModel: ReadPrayerViewModel by viewModels()

    companion object {
        const val ARG_BOOK_ID = "book_id"
        const val ARG_PRAYER_TITLE = "prayer_title"
        const val ARG_PRAYER_ID = "prayer_id"
        fun getBundle(bookId: Int, prayerId:Int, prayerTitle:String): Bundle {
            return bundleOf(
                ARG_BOOK_ID to bookId,
                ARG_PRAYER_ID to prayerId,
                ARG_PRAYER_TITLE to prayerTitle
            )
        }
    }

}