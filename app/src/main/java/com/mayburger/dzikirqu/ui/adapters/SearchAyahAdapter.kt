package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemSearchAyahBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemSearchAyahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class SearchAyahAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemSearchAyahViewModel>
    private var mListener: Callback? = null
    private var hasBismillah = false

    init {
        this.data = ArrayList()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 0
        const val VIEW_TYPE_LOADING = 2
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) {
                data.size
        } else {
            1
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
                val viewBinding = ItemSearchAyahBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SurahViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BaseViewHolder.EmptyViewHolder(viewBinding)
            }
        }
    }


    var isLoaded = false

    fun addItems(data: ArrayList<ItemSearchAyahViewModel>) {
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
        fun onSelectedItem(surah: AyahDataModel)
    }

    inner class SurahViewHolder(private val mBinding: ItemSearchAyahBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = if (hasBismillah) {
                    data[position-1]
                } else {
                    data[position]
                }
                mBinding.root.setOnClickListener { mListener?.onSelectedItem(viewModel.data) }
                viewModel.isIndexOdd.set(position % 2 == 0)
                mBinding.viewModel = viewModel
            }
        }
    }
}