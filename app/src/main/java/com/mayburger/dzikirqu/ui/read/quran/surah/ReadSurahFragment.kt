package com.mayburger.dzikirqu.ui.read.quran.surah

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentReadSurahBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.ui.adapters.AyahAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import com.mayburger.dzikirqu.ui.read.quran.options.ReadQuranBSDFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_read_surah.*
import javax.inject.Inject


@AndroidEntryPoint
class ReadSurahFragment : BaseFragment<FragmentReadSurahBinding, ReadSurahViewModel>(),
    ReadSurahNavigator, AyahAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_read_surah
    override val viewModel: ReadSurahViewModel by viewModels()

    @Inject
    lateinit var ayahAdapter: AyahAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel._surahId.value =
            requireActivity().intent.getIntExtra(ReadActivity.EXTRA_SURAH_ID, -1)
        rvAyah.adapter = ayahAdapter
        ayahAdapter.setListener(this)
    }

    override fun onLoadQuran() {
        var position = requireActivity().intent.getIntExtra(ReadActivity.EXTRA_VERSE_ID, 0)
        if (position != 0) {
            position -= 1
        }
        rvAyah.scrollToPosition(position)
    }

    override fun onSelectedItem(ayah: AyahDataModel) {
        viewModel.surah.value?.let {
            showBottomSheet(ReadQuranBSDFragment.newInstance(it, ayah), ReadQuranBSDFragment.TAG)
        }
    }
}