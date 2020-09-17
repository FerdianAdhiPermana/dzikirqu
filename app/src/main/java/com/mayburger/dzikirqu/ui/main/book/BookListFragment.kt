package com.mayburger.dzikirqu.ui.main.book

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentBookListBinding
import com.mayburger.dzikirqu.model.PrayerDataModele
import com.mayburger.dzikirqu.ui.adapters.BookListAdapter
import com.mayburger.dzikirqu.ui.base.BaseBSDFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_book_list.*
import javax.inject.Inject

@AndroidEntryPoint
class BookListFragment : BaseBSDFragment<FragmentBookListBinding, BookListViewModel>(),
    BookListAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_book_list
    override val viewModel: BookListViewModel by viewModels()

    @Inject
    lateinit var bookListAdapter: BookListAdapter

    companion object {
        const val ARG_TYPE = "type"
        fun getBundle(type: String): Bundle {
            return bundleOf(ARG_TYPE to type)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel._bookType.value = arguments?.getString(ARG_TYPE)
        rvBookList.adapter = bookListAdapter
        bookListAdapter.setListener(this)
    }

    override fun onSelectedItem(restaurant: PrayerDataModele.Data) {

    }
}