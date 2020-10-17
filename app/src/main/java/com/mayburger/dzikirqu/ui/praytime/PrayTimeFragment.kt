package com.mayburger.dzikirqu.ui.praytime

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentPrayTimeBinding
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pray_time.*


@AndroidEntryPoint
class PrayTimeFragment : BaseFragment<FragmentPrayTimeBinding, PrayTimeViewModel>(), PrayTimeNavigator{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_pray_time
    override val viewModel: PrayTimeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel.prayerTime.observe(viewLifecycleOwner) {
            viewModel.buildPrayerTime(it)
            viewModel.buildPrayers(it)
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    motionLayout.transitionToStart()
                    NavHostFragment.findNavController(this@PrayTimeFragment).popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onClickBack() {
        motionLayout.transitionToStart()
        NavHostFragment.findNavController(this@PrayTimeFragment).popBackStack()
    }

}