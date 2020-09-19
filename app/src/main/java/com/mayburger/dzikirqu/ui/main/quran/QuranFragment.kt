package com.mayburger.dzikirqu.ui.main.quran

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentQuranBinding
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuranFragment : BaseFragment<FragmentQuranBinding, QuranViewModel>(), QuranNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_quran
    override val viewModel: QuranViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
    }
}