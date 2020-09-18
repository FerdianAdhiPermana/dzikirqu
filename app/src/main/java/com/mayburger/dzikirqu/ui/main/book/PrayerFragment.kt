package com.mayburger.dzikirqu.ui.main.book

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
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
        const val ARG_BOOK_TITLE = "book_title"
        const val ARG_BOOK_DESC = "book_desc"
        fun getBundle(id: String,name:String,desc:String): Bundle {
            return bundleOf(
                ARG_BOOK_ID to id,
                ARG_BOOK_TITLE to name,
                ARG_BOOK_DESC to desc
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel._bookId.value = arguments?.getString(ARG_BOOK_ID)
        viewModel.bookTitle.value = arguments?.getString(ARG_BOOK_TITLE)
        viewModel.bookDesc.value = arguments?.getString(ARG_BOOK_DESC)
        setUpAdapter()
    }

    fun setUpAdapter(){
        rvPrayer.adapter = prayerAdapter
        prayerAdapter.setListener(this)
    }

    override fun onSelectedItem(restaurant: PrayerDataModel.Data) {

    }
}