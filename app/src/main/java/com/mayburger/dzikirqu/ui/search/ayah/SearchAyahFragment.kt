package com.mayburger.dzikirqu.ui.search.ayah

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentSearchAyahBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.ui.adapters.SearchAyahAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_ayah.*
import javax.inject.Inject


@AndroidEntryPoint
class SearchAyahFragment : BaseFragment<FragmentSearchAyahBinding, SearchAyahViewModel>(),
    SearchAyahNavigator,SearchAyahAdapter.Callback{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_search_ayah
    override val viewModel: SearchAyahViewModel by viewModels()

    @Inject
    lateinit var searchAyahAdapter: SearchAyahAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        setUpAdapter()
    }

    fun setUpAdapter() {
        rvAyah.adapter = searchAyahAdapter
        searchAyahAdapter.setListener(this)
    }

    override fun onSelectedItem(surah: AyahDataModel) {

    }
}