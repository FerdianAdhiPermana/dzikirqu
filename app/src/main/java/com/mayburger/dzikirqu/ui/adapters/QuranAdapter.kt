package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemBismillahBinding
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemQuranBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemQuranViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class QuranAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemQuranViewModel>
    private var mListener: Callback? = null
    private var hasBismillah = false

    init {
        this.data = ArrayList()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 0
        const val VIEW_TYPE_BISMILLAH = 1
        const val VIEW_TYPE_LOADING = 2
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) {
            if (hasBismillah) {
                data.size + 1
            } else {
                data.size
            }
        } else {
            2
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty()) {
            if (position == 0 && hasBismillah) {
                VIEW_TYPE_BISMILLAH
            } else {
                VIEW_TYPE_NORMAL
            }
        } else {
            VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val viewBinding = ItemQuranBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SurahViewHolder(viewBinding)
            }
            VIEW_TYPE_BISMILLAH->{
                val viewBinding = ItemBismillahBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SurahBismillahViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BaseViewHolder.EmptyViewHolder(viewBinding)
            }
        }
    }


    var isLoaded = false

    fun addItems(data: ArrayList<ItemQuranViewModel>) {
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

    fun isHasBismillah(hasBismillah: Boolean) {
        this.hasBismillah = hasBismillah
    }

    interface Callback {
        fun onSelectedItem(surah: AyahDataModel)
    }

    class SurahBismillahViewHolder(private val mBinding: ItemBismillahBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    inner class SurahViewHolder(private val mBinding: ItemQuranBinding) :
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