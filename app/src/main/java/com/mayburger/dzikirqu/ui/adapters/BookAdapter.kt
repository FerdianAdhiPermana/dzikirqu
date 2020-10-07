package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemBookEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemBookGridBinding
import com.mayburger.dzikirqu.databinding.ItemBookHorizontalBinding
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder
import com.mayburger.dzikirqu.util.ext.ViewUtils.dpToPx


class BookAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemBookViewModel>
    private var mListener: Callback? = null

    init {
        this.data = ArrayList()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_LOADING = 2
        const val VIEW_TYPE_GRID = 3
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) {
            data.size
        } else {
            2
        }
    }

    var asGrid = false

    fun asGrid() {
        asGrid = true
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty()) {
            if (asGrid) {
                VIEW_TYPE_GRID
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
                val viewBinding = ItemBookHorizontalBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookHorizontalViewHolder(viewBinding)
            }
            VIEW_TYPE_GRID -> {
                val viewBinding = ItemBookGridBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookGridViewHolder(viewBinding)
            }
            VIEW_TYPE_LOADING -> {
                val viewBinding = ItemBookEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookEmptyViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemBookEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BookEmptyViewHolder(viewBinding)
            }
        }
    }


    var isLoaded = false

    fun addItems(data: ArrayList<ItemBookViewModel>) {
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
        fun onSelectedItem(book: BookDataModel)
    }

    inner class BookEmptyViewHolder(private val mBinding: ItemBookEmptyBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    inner class BookHorizontalViewHolder(private val mBinding: ItemBookHorizontalBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                if(position == 0){
                    val params = mBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.setMargins(dpToPx(16),dpToPx(4),dpToPx(4),dpToPx(4))
                    mBinding.root.layoutParams = params
                }
                val viewModel = data[position]
                mBinding.root.setOnClickListener { mListener?.onSelectedItem(data[position].data) }
                mBinding.viewModel = viewModel
            }
        }
    }

    inner class BookGridViewHolder(private val mBinding: ItemBookGridBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                if(position == 0){
                    val params = mBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.setMargins(dpToPx(16),dpToPx(4),dpToPx(4),dpToPx(4))
                    mBinding.root.layoutParams = params
                }
                val viewModel = data[position]
                mBinding.root.setOnClickListener { mListener?.onSelectedItem(data[position].data) }
                mBinding.viewModel = viewModel
            }
        }
    }
}