package com.mayburger.dzikirqu.ui.read.quran.options

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentReadQuranBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.model.QuranLastRead
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.model.events.QuranLastReadEvent
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.util.rx.RxBus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReadQuranBSDFragment : BaseFragment<FragmentReadQuranBinding, ReadQuranBSDViewModel>(),
    ReadQuranBSDNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_read_quran_bsd
    override val viewModel: ReadQuranBSDViewModel by viewModels()

    companion object {
        const val TAG = "ReadQuranBSDFragment"
        const val EXTRA_AYAH = "EXTRA_AYAH"
        const val EXTRA_SURAH = "EXTRA_SURAH"
        fun newInstance(surah: SurahDataModel, ayah: AyahDataModel): ReadQuranBSDFragment {
            val fragment = ReadQuranBSDFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_SURAH, surah)
            bundle.putParcelable(EXTRA_AYAH, ayah)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigator = this
    }

    override fun onClickLastRead() {
        arguments?.getParcelable<SurahDataModel>(EXTRA_SURAH)?.let { surah ->
            arguments?.getParcelable<AyahDataModel>(EXTRA_AYAH)?.let { ayah ->
                viewModel.dataManager.quranLastRead = QuranLastRead(surah, ayah)
            }
        }
        showSnackBar("Marked as last read")
        RxBus.getDefault().send(QuranLastReadEvent())
    }
}