package com.mayburger.dzikirqu.ui.main.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.Constants
import com.mayburger.dzikirqu.databinding.FragmentHomeBinding
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.main.book.prayer.PrayerFragment
import com.mayburger.dzikirqu.ui.praytime.PrayTimeActivity
import com.mayburger.dzikirqu.ui.read.ReadActivity
import com.mayburger.dzikirqu.ui.surah.SurahActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_import_quran.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
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
        viewModel.prayerTime.observe(viewLifecycleOwner) {
            viewModel.buildPrayerTime(it)
        }
        buildAdapter()
    }

    override fun onClickPrayTime() {
        PrayTimeActivity.startActivity(requireActivity())
    }

    override fun onClickReadQuran() {
        if (!viewModel.dataManager.isQuranLoaded) {
            val dialog = Dialog(requireActivity()).apply {
                val dialog = this
                this.setContentView(
                    LayoutInflater.from(requireActivity())
                        .inflate(R.layout.dialog_import_quran, null, false).rootView
                )
                this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                this.importo.setOnClickListener {
                    this.setCancelable(false)
                    this.motionLayout.transitionToEnd()
                    CoroutineScope(IO).launch {
                        viewModel.setUpQuran(
                            requireActivity(),
                            dialog.importDescription,
                            dialog.importProgress
                        ) {
                            SurahActivity.startActivity(requireActivity())
                            viewModel.dataManager.isQuranLoaded = true
                            dialog.dismiss()
                        }
                    }
                }
            }
            dialog.show()
        } else {
            SurahActivity.startActivity(requireActivity())
        }
    }

    override fun onClickLastRead() {
        val lastRead = viewModel.dataManager.quranLastRead
        ReadActivity.start(
            requireActivity(),
            surahId = lastRead?.surah?.id,
            verseId = lastRead?.verse?.id
        )
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