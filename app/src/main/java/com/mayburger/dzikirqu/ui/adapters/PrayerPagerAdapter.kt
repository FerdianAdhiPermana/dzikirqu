package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.PagePrayerBinding
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.PagePrayerViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder


class PrayerPagerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<PagePrayerViewModel>
    private var mListener: Callback? = null
    var isPager = false

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
                val viewBinding = PagePrayerBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                PrayerPagerViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BaseViewHolder.EmptyViewHolder(viewBinding)
            }
        }
    }


    fun addItems(data: ArrayList<PagePrayerViewModel>) {
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
        fun onSelectedItem(prayer: PrayerDataModel)
    }

    inner class PrayerPagerViewHolder(private val mBinding: PagePrayerBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = data[position]
                mBinding.viewModel = viewModel
            }
        }
    }
}