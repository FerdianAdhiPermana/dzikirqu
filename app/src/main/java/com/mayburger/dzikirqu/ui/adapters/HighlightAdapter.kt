package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemHighlightBinding
import com.mayburger.dzikirqu.databinding.ItemHighlightEmptyBinding
import com.mayburger.dzikirqu.db.AppDatabase
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemHighlightViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class HighlightAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemHighlightViewModel>
    private var mListener: Callback? = null

    init {
        this.data = ArrayList()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_LOADING = 2
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) {
            data.size
        } else {
            2
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val viewBinding = ItemHighlightBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                HighlightViewHolder(viewBinding)
            }
            VIEW_TYPE_LOADING -> {
                val viewBinding = ItemHighlightEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                HighlightEmptyViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemHighlightEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                HighlightEmptyViewHolder(viewBinding)
            }
        }
    }


    var isLoaded = false

    fun addItems(data: ArrayList<ItemHighlightViewModel>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clearItems() {
        data.clear()
        notifyDataSetChanged()
    }

    fun setListener(listener: Callback) {
        this.mListener = listener
    }

    interface Callback {
        fun onSelectedItem(restaurant: BookDataModel)
    }

    inner class HighlightEmptyViewHolder(private val mBinding: ItemHighlightEmptyBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    inner class HighlightViewHolder(private val mBinding: ItemHighlightBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = data[position]
                mBinding.viewModel = viewModel
            }
        }
    }
}