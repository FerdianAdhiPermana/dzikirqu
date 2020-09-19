package com.mayburger.dzikirqu.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentHomeBinding
import com.mayburger.dzikirqu.ui.adapters.HighlightAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var highlightAdapter: HighlightAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel.prayerTime.observe(viewLifecycleOwner, {
            viewModel.buildPrayerTime(it)
        })
        buildAdapter()
    }

    fun buildAdapter(){
        rvHighlight.adapter = highlightAdapter
    }
}