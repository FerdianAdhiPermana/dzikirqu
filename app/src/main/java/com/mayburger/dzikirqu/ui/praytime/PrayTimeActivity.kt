package com.mayburger.dzikirqu.ui.praytime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ActivityPrayTimeBinding
import com.mayburger.dzikirqu.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_pray_time.*


@AndroidEntryPoint
class PrayTimeActivity : BaseActivity<ActivityPrayTimeBinding, PrayTimeViewModel>(), PrayTimeNavigator{

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_pray_time
    override val viewModel: PrayTimeViewModel by viewModels()

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, PrayTimeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.navigator = this
        viewModel.prayerTime.observe(this) {
            viewModel.buildPrayerTime(it)
            viewModel.buildPrayers(it)
        }
        delay(200){
            motionLayout.transitionToEnd()
        }
    }

    override fun onBackPressed() {
        motionLayout.transitionToStart()
        delay(300){
            finishActivity()
        }
    }

    override fun onClickBack() {
        motionLayout.transitionToStart()
        delay(300){
            finishActivity()
        }
    }

}