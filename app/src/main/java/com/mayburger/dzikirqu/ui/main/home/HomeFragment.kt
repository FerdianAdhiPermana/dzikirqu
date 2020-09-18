package com.mayburger.dzikirqu.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentHomeBinding
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.main.book.PrayerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator,
    BookAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var bookAdapter: BookAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        navController = Navigation.findNavController(view)
        rvBooks.adapter = bookAdapter
        bookAdapter.setListener(this)
//        CoroutineScope(IO).launch {
//            val books = viewModel.dataManager.getBooks()
//            books.filter {
//                it.data.status != "3" && it.data.type != "qur"
//            }.map { book ->
//                val hey = viewModel.dataManager.getBookData(book.data.type).filter {
//                    it.data.language == book.data.language
//                }.map {it ->
//                    PrayerDataModel.create(it.data)
//                }
//                Firebase.firestore.collection("books").add(
//                    BookDataModel.create(book.data,hey)
//                )
////                if (hey.isNotEmpty()) {
////                    Firebase.firestore.collection("books").add(
////                        BookDataModel.create(book.data,PrayerDataModel.create(
////                            hey[0].data
////                        ))
////                    )
////                }
//            }
//        }
    }

    override fun onSelectedItem(book: BookDataModel) {
        val fragment = PrayerFragment()
        fragment.arguments = PrayerFragment.getBundle(book.id,book.title,book.desc)
        fragment.show(requireActivity().supportFragmentManager, "")
    }
}