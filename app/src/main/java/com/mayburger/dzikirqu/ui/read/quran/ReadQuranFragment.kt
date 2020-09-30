package com.mayburger.dzikirqu.ui.read.quran

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentReadQuranBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.model.HighlightDataModel
import com.mayburger.dzikirqu.ui.adapters.QuranAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_read_quran.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ReadQuranFragment : BaseFragment<FragmentReadQuranBinding, ReadQuranViewModel>(),
    ReadQuranNavigator,QuranAdapter.Callback{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_read_quran
    override val viewModel: ReadQuranViewModel by viewModels()

    @Inject
    lateinit var quranAdapter: QuranAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel._surahId.value = requireActivity().intent.getIntExtra(ReadActivity.EXTRA_SURAH_ID, 1)
        quranAdapter.isHasBismillah(viewModel._surahId.value != 1)
        rvQuran.adapter = quranAdapter
        quranAdapter.setListener(this)
    }

    override fun onSelectedItem(surah: AyahDataModel) {
        onError("SAVED TO HIGHLIGHTS")
        CoroutineScope(IO).launch{
            viewModel.dataManager.insertHighlight(
                HighlightDataModel(
                    viewModel.surah.value?.name?:"",
                    "Verse number ${surah.id}",
                    viewModel._surahId.value?:0,
                    surah.id,
                    language=viewModel.dataManager.language,
                    type=1
                )
            )
        }
    }
}