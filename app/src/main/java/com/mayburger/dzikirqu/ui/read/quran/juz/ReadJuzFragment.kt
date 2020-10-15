package com.mayburger.dzikirqu.ui.read.quran.juz

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentReadJuzBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.ui.adapters.AyahAdapter
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemAyahViewModel
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.read.ReadActivity
import com.mayburger.dzikirqu.ui.read.quran.options.ReadQuranBSDFragment
import com.mayburger.dzikirqu.util.QuranUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_read_juz.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ReadJuzFragment : BaseFragment<FragmentReadJuzBinding, ReadJuzViewModel>(),
    ReadJuzNavigator, AyahAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_read_juz
    override val viewModel: ReadJuzViewModel by viewModels()

    @Inject
    lateinit var ayahAdapter: AyahAdapter

    lateinit var layoutManager:LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        viewModel.navigator = this
        viewModel._juzId.value =
            requireActivity().intent.getIntExtra(ReadActivity.EXTRA_JUZ_ID, -1)
        viewModel.title.set("Juz ${viewModel._juzId.value}")
        viewModel.subtitle.set("Juz ${QuranUtils.getJuzNames()[viewModel._juzId.value?.minus(1) ?: 0]}")
        rvAyah.adapter = ayahAdapter
        layoutManager = LinearLayoutManager(requireActivity())
        rvAyah.layoutManager = layoutManager
        rvAyah.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                CoroutineScope(IO).launch {
                    val position = layoutManager.findFirstVisibleItemPosition()
                    val ayah = viewModel.ayah.value?.get(position)
                    if (ayah is ItemAyahViewModel) {
                        viewModel.subtitle.set(viewModel.dataManager.getSurahById(ayah.data.surahId)[0].name)
                    }
                }
            }
        })
        ayahAdapter.setListener(this)
    }

    override fun onLoadQuran() {
    }

    override fun onSelectedItem(ayah: AyahDataModel) {
        CoroutineScope(IO).launch {
            val surah = viewModel.dataManager.getSurahById(ayah.surahId)[0]
            CoroutineScope(Main).launch {
                showBottomSheet(
                    ReadQuranBSDFragment.newInstance(surah, ayah),
                    ReadQuranBSDFragment.TAG
                )
            }
        }
    }
}