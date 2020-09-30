package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemPrayerEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemSurahBinding
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemSurahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class SurahAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemSurahViewModel>
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
                val viewBinding = ItemSurahBinding
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

    fun addItems(data: ArrayList<ItemSurahViewModel>) {
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
        fun onSelectedItem(surah: SurahDataModel)
    }

    inner class SurahViewHolder(private val mBinding: ItemSurahBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = data[position]
                mBinding.root.setOnClickListener { mListener?.onSelectedItem(data[position].data) }
                mBinding.viewModel = viewModel
            }
        }
    }
}