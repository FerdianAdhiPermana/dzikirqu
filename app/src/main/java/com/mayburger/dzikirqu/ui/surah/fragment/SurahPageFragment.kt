package com.mayburger.dzikirqu.ui.surah.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.SurahTypeConstants
import com.mayburger.dzikirqu.databinding.FragmentSurahPageBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.ui.adapters.JuzAdapter
import com.mayburger.dzikirqu.ui.adapters.QuranBookmarkAdapter
import com.mayburger.dzikirqu.ui.adapters.SurahAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_surah_page.*
import javax.inject.Inject


@AndroidEntryPoint
class SurahPageFragment : BaseFragment<FragmentSurahPageBinding, SurahPageViewModel>(),
    SurahPageNavigator,
    SurahAdapter.Callback,JuzAdapter.Callback,QuranBookmarkAdapter.Callback{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_surah_page
    override val viewModel: SurahPageViewModel by viewModels()

    @Inject
    lateinit var surahAdapter: SurahAdapter

    @Inject
    lateinit var juzAdapter: JuzAdapter

    @Inject
    lateinit var quranBookmarkAdapter: QuranBookmarkAdapter

    var type: Int? = 0

    companion object {
        const val EXTRA_TYPE = "EXTRA_TYPE"
        fun newInstance(type: Int): SurahPageFragment {
            val fragment = SurahPageFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        type = arguments?.getInt(EXTRA_TYPE)
        viewModel.type.set(type)
        when (type) {
            SurahTypeConstants.TYPE_SURAH -> {
                rvJuz.visibility = View.GONE
                rvBookmarks.visibility = View.GONE
                rvSurah.visibility = View.VISIBLE
                rvSurah.adapter = surahAdapter
                surahAdapter.setListener(this)
                viewModel._refreshSurahs.value = true
            }
            SurahTypeConstants.TYPE_JUZ -> {
                rvJuz.visibility = View.VISIBLE
                rvSurah.visibility = View.GONE
                rvBookmarks.visibility = View.GONE
                rvJuz.adapter = juzAdapter
                juzAdapter.setListener(this)
                viewModel._refreshJuz.value = true
            }
            SurahTypeConstants.TYPE_BOOKMARK -> {
                rvJuz.visibility = View.GONE
                rvSurah.visibility = View.GONE
                rvBookmarks.visibility = View.VISIBLE
                rvBookmarks.adapter = quranBookmarkAdapter
                quranBookmarkAdapter.setListener(this)
                viewModel._refreshBookmarks.value = true
            }
        }
    }

    override fun onSelectedSurah(surah: SurahDataModel) {
        ReadActivity.start(requireActivity(), surahId = surah.id)
    }

    override fun onSelectedJuz(juz: Int) {
        ReadActivity.start(requireActivity(),juzId = juz)
    }

    override fun onSelectedBookmark(ayah: AyahDataModel) {
        ReadActivity.start(requireActivity(),surahId = ayah.surah?.id,verseId = ayah.id)
    }
}