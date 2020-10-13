package com.mayburger.dzikirqu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.constants.Constants
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper
import com.mayburger.dzikirqu.data.room.AppRoomHelper
import com.mayburger.dzikirqu.databinding.ItemEmptyBinding
import com.mayburger.dzikirqu.databinding.ItemPrayerBinding
import com.mayburger.dzikirqu.databinding.PagePrayerBinding
import com.mayburger.dzikirqu.databinding.PagePrayerQuranBinding
import com.mayburger.dzikirqu.db.AppDatabase
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.SurahDataModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemPrayerViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.PagePrayerQuranViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.PagePrayerViewModel
import com.mayburger.dzikirqu.ui.base.BaseViewHolder
import com.mayburger.dzikirqu.ui.read.ReadActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class PrayerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val data: MutableList<ItemPrayerViewModel>
    private val readData: MutableList<PagePrayerViewModel>
    private var mListener: Callback? = null
    private var isReadMode = false

    init {
        this.data = ArrayList()
        this.readData = ArrayList()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_LOADING = 2
        const val VIEW_TYPE_PAGER = 3
        const val VIEW_TYPE_QURAN_LINK = 4
    }

    override fun getItemCount(): Int {
        return if (isReadMode) {
            if (readData.isNotEmpty()) {
                readData.size
            } else {
                2
            }
        } else {
            if (data.isNotEmpty()) {
                data.size
            } else {
                2
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isReadMode) {
            if (readData.isNotEmpty()) {
                val deepLink = Constants.getDeepLink().filter {
                    readData[position].data.arabic == it.value
                }
                if (deepLink.isEmpty()) {
                    VIEW_TYPE_PAGER
                } else {
                    if (deepLink[0].bookType == Constants.BOOK_TYPE_QURAN) {
                        VIEW_TYPE_QURAN_LINK
                    } else {
                        VIEW_TYPE_PAGER
                    }
                }
            } else {
                VIEW_TYPE_LOADING
            }
        } else {
            if (data.isNotEmpty()) {
                VIEW_TYPE_NORMAL
            } else {
                VIEW_TYPE_LOADING
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val viewBinding = ItemPrayerBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                PrayerListViewHolder(viewBinding)
            }
            VIEW_TYPE_PAGER -> {
                val viewBinding = PagePrayerBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                PrayerPagerViewHolder(viewBinding)
            }
            VIEW_TYPE_QURAN_LINK -> {
                val viewBinding = PagePrayerQuranBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                PrayerPagerQuranViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                BaseViewHolder.EmptyViewHolder(viewBinding)
            }
        }
    }


    fun addItems(data: ArrayList<ItemPrayerViewModel>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addReadItems(data: ArrayList<PagePrayerViewModel>) {
        this.readData.addAll(data)
        notifyDataSetChanged()
    }

    var isLoaded = false

    fun clearItems() {
        data.clear()
        readData.clear()
        notifyDataSetChanged()
    }

    fun isReadMode(isReadMode: Boolean) {
        this.isReadMode = isReadMode
    }

    fun setListener(listener: Callback) {
        this.mListener = listener
    }

    interface Callback {
        fun onSelectedItem(prayer: PrayerDataModel)
    }

    inner class PrayerListViewHolder(private val mBinding: ItemPrayerBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (data.isNotEmpty()) {
                val viewModel = data[position]
                viewModel.title.set("${(position.plus(1))}. ${data[position].data.title}")
                mBinding.root.setOnClickListener { mListener?.onSelectedItem(data[position].data) }
                mBinding.viewModel = viewModel
            }
        }
    }

    inner class PrayerPagerViewHolder(private val mBinding: PagePrayerBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (readData.isNotEmpty()) {
                val viewModel = readData[position]
                mBinding.viewModel = viewModel
            }
        }
    }

    inner class PrayerPagerQuranViewHolder(private val mBinding: PagePrayerQuranBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            if (readData.isNotEmpty()) {
                val deepLink = Constants.getDeepLink().filter {
                    readData[position].data.arabic == it.value
                }[0]
                CoroutineScope(IO).launch {
                    mBinding.viewModel = PagePrayerQuranViewModel(readData[position].data,
                        AppRoomHelper(
                            AppDatabase.invoke(mBinding.root.context),
                            AppHawkHelper()
                        ).getSurahById(deepLink.bookId ?: 1)[0],
                        object :
                            PagePrayerQuranViewModel.Callback {
                            override fun onClickItem(surah: SurahDataModel) {
                                ReadActivity.start(mBinding.root.context,surahId = surah.id)
                            }
                        }
                    )
                }
            }
        }
    }


}