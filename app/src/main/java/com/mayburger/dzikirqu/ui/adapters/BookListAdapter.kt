package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemBookListBinding
import com.mayburger.dzikirqu.databinding.ItemBookListEmptyBinding
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookListViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class BookListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemBookListViewModel>
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
                val viewBinding = ItemBookListBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookListViewHolder(viewBinding)
            }
            VIEW_TYPE_LOADING -> {
                val viewBinding = ItemBookListEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookEmptyViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemBookListEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookEmptyViewHolder(viewBinding)
            }
        }
    }


    fun addItems(data: ArrayList<ItemBookListViewModel>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    var isLoaded = false

    fun clearItems() {
        data.clear()
        notifyDataSetChanged()
    }

    fun setListener(listener: Callback) {
        this.mListener = listener
    }

    interface Callback {
        fun onSelectedItem(restaurant: PrayerDataModel.Data)
    }

    inner class BookEmptyViewHolder(private val mBinding: ItemBookListEmptyBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    inner class BookListViewHolder(private val mBinding: ItemBookListBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = data[position]
//                mBinding.root.setOnClickListener { mListener?.onSelectedItem(data[position].data) }
                mBinding.viewModel = viewModel
            }
        }
    }
}