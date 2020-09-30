package com.mayburger.dzikirqu.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemBookEmptyBinding

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(position: Int)


    class EmptyViewHolder(private val mBinding: ItemBookEmptyBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }
}
