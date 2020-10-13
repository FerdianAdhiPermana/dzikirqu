package com.mayburger.dzikirqu.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.Constants
import com.mayburger.dzikirqu.databinding.FragmentHomeBinding
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.main.book.prayer.PrayerFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator,
    BookAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var booksAdapter: BookAdapter

    companion object {
        const val TAG = "HomeFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel.prayerTime.observe(viewLifecycleOwner, {
            viewModel.buildPrayerTime(it)
        })
        buildAdapter()
    }

    override fun onClickPrayTime() {
        val extras = FragmentNavigatorExtras(
            next to "next",
            time to "time",
            until to "until",
            masjid to "masjid"
        )
        findNavController(this).navigate(R.id.prayTime, null, null, extras)
    }

    override fun onClickReadQuran() {
        findNavController(this).navigate(R.id.surahFragment, null, null, null)
    }

    override fun onClickLastRead() {
        val lastRead = viewModel.dataManager.quranLastRead
        ReadActivity.start(requireActivity(),surahId = lastRead?.surah?.id, verseId = lastRead?.verse?.id)
    }


    fun buildAdapter() {
        rvBooks.adapter = booksAdapter
        booksAdapter.setListener(this)
    }

    override fun onSelectedItem(book: BookDataModel) {
        if (book.type == Constants.BOOK_TYPE_PRAYER) {
            val fragment = PrayerFragment()
            fragment.arguments = PrayerFragment.getBundle(book.id, book.title, book.desc)
            showBottomSheet(fragment, PrayerFragment.TAG)
        }
    }
}