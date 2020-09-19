package com.mayburger.dzikirqu.ui.main.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.constants.BookConstants
import com.mayburger.dzikirqu.databinding.FragmentBookBinding
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.main.book.prayer.PrayerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_book.*
import javax.inject.Inject


@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding, BookViewModel>(), BookNavigator,
    BookAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_book
    override val viewModel: BookViewModel by viewModels()

    @Inject
    lateinit var bookAdapter: BookAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        rvBooks.adapter = bookAdapter
        bookAdapter.setListener(this)
    }

    override fun onSelectedItem(book: BookDataModel) {
        if (book.type == BookConstants.BOOK_TYPE_PRAYER) {
            val fragment = PrayerFragment()
            fragment.arguments = PrayerFragment.getBundle(book.id, book.title, book.desc)
            fragment.show(requireActivity().supportFragmentManager, "")
        }
    }

}