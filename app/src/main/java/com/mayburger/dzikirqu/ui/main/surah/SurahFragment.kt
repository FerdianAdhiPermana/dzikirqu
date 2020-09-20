package com.mayburger.dzikirqu.ui.main.surah

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentSurahBinding
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.ui.adapters.SurahAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_surah.*
import javax.inject.Inject


@AndroidEntryPoint
class SurahFragment : BaseFragment<FragmentSurahBinding, SurahViewModel>(), SurahNavigator,
    SurahAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_surah
    override val viewModel: SurahViewModel by viewModels()


    @Inject
    lateinit var surahAdapter: SurahAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        rvSurah.adapter = surahAdapter
        surahAdapter.setListener(this)
    }

    override fun onSelectedItem(restaurant: SurahDataModel) {

    }
}