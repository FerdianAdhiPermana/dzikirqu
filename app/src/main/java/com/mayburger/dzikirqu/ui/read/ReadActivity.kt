package com.mayburger.dzikirqu.ui.read

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ActivityReadBinding
import com.mayburger.dzikirqu.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadActivity : BaseActivity<ActivityReadBinding, ReadViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_read
    override val viewModel: ReadViewModel by viewModels()

    companion object{
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_BOOK_ID = "extra_book_id"
        fun start(context: Context, type:String?=null, id:String?=null){
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra(EXTRA_TYPE, type)
            intent.putExtra(EXTRA_TYPE, id)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
    }
}