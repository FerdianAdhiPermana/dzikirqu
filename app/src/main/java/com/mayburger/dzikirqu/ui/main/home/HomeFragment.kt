package com.mayburger.dzikirqu.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentHomeBinding
import com.mayburger.dzikirqu.model.HighlightDataModel
import com.mayburger.dzikirqu.ui.adapters.HighlightAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator,HighlightAdapter.Callback{

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
        highlightAdapter.setListener(this)
    }

    override fun onSelectedItem(highlight: HighlightDataModel) {
        when(highlight.type){
            1->{
                ReadActivity.start(requireActivity(),surahId = highlight.id)
            }
        }
    }

    override fun onDeleteItem(highlight: HighlightDataModel) {
        CoroutineScope(IO).launch{
            viewModel.dataManager.deleteHighlight(highlight)
            viewModel._refreshHighlights.postValue(true)
        }
    }
}