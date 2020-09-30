package com.mayburger.dzikirqu.ui.search.surah

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentSearchSurahBinding
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.ui.adapters.SurahAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_surah.*
import javax.inject.Inject


@AndroidEntryPoint
class SearchSurahFragment : BaseFragment<FragmentSearchSurahBinding, SearchSurahViewModel>(),
    SearchSurahNavigator,SurahAdapter.Callback{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_search_surah
    override val viewModel: SearchSurahViewModel by viewModels()

    @Inject
    lateinit var surahAdapter: SurahAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        setUpAdapter()
    }
    fun setUpAdapter() {
        rvSurah.adapter = surahAdapter
        surahAdapter.setListener(this)
    }

    override fun onSelectedItem(surah: SurahDataModel) {
        ReadActivity.start(requireActivity(),surahId = surah.id)
    }

}