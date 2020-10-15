package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemAyahBinding
import com.mayburger.dzikirqu.databinding.ItemBismillahBinding
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemSurahDetailBinding
import com.mayburger.dzikirqu.model.AyahDataModel
import com.mayburger.dzikirqu.model.BismillahObject
import com.mayburger.dzikirqu.model.SurahObject
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemAyahViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class AyahAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<Any>
    private var mListener: Callback? = null

    init {
        this.data = ArrayList()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 0
        const val VIEW_TYPE_BISMILLAH = 1
        const val VIEW_TYPE_LOADING = 2
        const val VIEW_TYPE_SURAH_DETAIL = 3
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
            when {
                data[position] is BismillahObject -> {
                    VIEW_TYPE_BISMILLAH
                }
                data[position] is SurahObject -> {
                    VIEW_TYPE_SURAH_DETAIL
                }
                else -> {
                    VIEW_TYPE_NORMAL
                }
            }
        } else {
            VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val viewBinding = ItemAyahBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SurahViewHolder(viewBinding)
            }
            VIEW_TYPE_BISMILLAH -> {
                val viewBinding = ItemBismillahBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SurahBismillahViewHolder(viewBinding)
            }
            VIEW_TYPE_SURAH_DETAIL -> {
                val viewBinding = ItemSurahDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SurahDetailViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BaseViewHolder.EmptyViewHolder(viewBinding)
            }
        }
    }


    var isLoaded = false

    fun addItems(data: ArrayList<Any>) {
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
        fun onSelectedItem(ayah: AyahDataModel)
    }

    class SurahBismillahViewHolder(private val mBinding: ItemBismillahBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    inner class SurahDetailViewHolder(private val mBinding: ItemSurahDetailBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            val viewModel = data[position] as SurahObject
            mBinding.viewModel = viewModel
        }
    }

    inner class SurahViewHolder(private val mBinding: ItemAyahBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = data[position] as ItemAyahViewModel
                mBinding.root.setOnClickListener { mListener?.onSelectedItem(viewModel.data) }
                viewModel.isIndexOdd.set(position % 2 == 0)
                mBinding.viewModel = viewModel
            }
        }
    }
}