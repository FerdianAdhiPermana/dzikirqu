package com.mayburger.dzikirqu.ui.main.book

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentPrayerBinding
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.adapters.PrayerAdapter
import com.mayburger.dzikirqu.ui.base.BaseBSDFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_prayer.*
import javax.inject.Inject

@AndroidEntryPoint
class PrayerFragment : BaseBSDFragment<FragmentPrayerBinding, PrayerViewModel>(),
    PrayerAdapter.Callback,PrayerNavigator{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_prayer
    override val viewModel: PrayerViewModel by viewModels()

    @Inject
    lateinit var prayerAdapter: PrayerAdapter

    companion object {
        const val ARG_BOOK_ID = "book_id"
        fun getBundle(type: String): Bundle {
            return bundleOf(ARG_BOOK_ID to type)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel._bookId.value = arguments?.getString(ARG_BOOK_ID)
        setUpAdapter()
    }

    fun setUpAdapter(){
        rvPrayer.adapter = prayerAdapter
        prayerAdapter.setListener(this)
    }

    override fun onSelectedItem(restaurant: PrayerDataModel.Data) {

    }
}