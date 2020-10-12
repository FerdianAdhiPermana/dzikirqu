package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.PagePrayerBinding
import com.mayburger.dzikirqu.ui.adapters.viewmodels.PagePrayerViewModel


class PrayerPagerAdapter : PagerAdapter() {


    private val data: MutableList<PagePrayerViewModel>

    init {
        data = ArrayList()
    }

    fun addItems(data:ArrayList<PagePrayerViewModel>){
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clearItems() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return if(data.isNotEmpty()){
            data.size
        } else{
            1
        }
    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        return if(data.isNotEmpty()){
            val viewBinding = PagePrayerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            viewBinding.viewModel = data[position]
            viewBinding.root
        } else{
            val viewBinding = ItemEmptyBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            viewBinding.root
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}