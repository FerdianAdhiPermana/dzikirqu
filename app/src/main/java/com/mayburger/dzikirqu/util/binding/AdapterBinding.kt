package com.mayburger.dzikirqu.util.binding

import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.ui.adapters.*
import com.mayburger.dzikirqu.ui.adapters.viewmodels.*


object AdapterBinding {
//    @BindingAdapter("checkoutAdapter")
//    @JvmStatic
//    fun addCheckoutItems(
//        recyclerView: RecyclerView,
//        items: MutableLiveData<ArrayList<ItemCheckoutViewModel>>
//    ) {
//        val adapter = recyclerView.adapter as CheckoutAdapter?
//        if (adapter != null) {
//            items.value?.let {
//                adapter.clearItems()
//                adapter.addItems(it)
//            }
//        }
//    }

    @BindingAdapter("booksAdapter")
    @JvmStatic
    fun addBooksItems(
        recyclerView: RecyclerView,
        items: LiveData<List<ItemBookViewModel>>
    ) {
        val adapter = recyclerView.adapter as BookAdapter?
        if (adapter != null) {
            items.value?.let {
                adapter.clearItems()
                adapter.addItems(ArrayList(it))
                if (!adapter.isLoaded) {
                    val context = recyclerView.context;
                    val controller =
                        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade);
                    recyclerView.layoutAnimation = controller;
                    recyclerView.scheduleLayoutAnimation()
                    adapter.isLoaded = true
                }
            }
        }
    }

    @BindingAdapter("surahAdapter")
    @JvmStatic
    fun addSurahItems(
        recyclerView: RecyclerView,
        items: LiveData<List<ItemSurahViewModel>>
    ) {
        val adapter = recyclerView.adapter as SurahAdapter?
        if (adapter != null) {
            items.value?.let {
                adapter.clearItems()
                adapter.addItems(ArrayList(it))
            }
            if (!adapter.isLoaded) {
                val context = recyclerView.context;
                val controller =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade);
                recyclerView.layoutAnimation = controller;
                recyclerView.scheduleLayoutAnimation()
                adapter.isLoaded = true
            }
        }
    }

    @BindingAdapter("quranAdapter")
    @JvmStatic
    fun addAyahs(
        recyclerView: RecyclerView,
        items: LiveData<List<ItemQuranViewModel>>
    ) {
        val adapter = recyclerView.adapter as QuranAdapter?
        if (adapter != null) {
            items.value?.let {
                adapter.clearItems()
                adapter.addItems(ArrayList(it))
            }
        }
    }

    @BindingAdapter("prayerAdapter")
    @JvmStatic
    fun addBookListItems(
        recyclerView: RecyclerView,
        items: LiveData<List<ItemPrayerViewModel>>
    ) {
        val adapter = recyclerView.adapter as PrayerAdapter?
        if (adapter != null) {
            items.value?.let {
                adapter.clearItems()
                adapter.addItems(ArrayList(it))
                if (!adapter.isLoaded) {
                    val context = recyclerView.context;
                    val controller =
                        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade);
                    recyclerView.layoutAnimation = controller;
                    recyclerView.scheduleLayoutAnimation()
                    adapter.isLoaded = true
                }
            }
        }
    }

    @BindingAdapter("highlightAdapter")
    @JvmStatic
    fun addHighlights(
        recyclerView: RecyclerView,
        items: LiveData<List<ItemHighlightViewModel>>
    ) {
        val adapter = recyclerView.adapter as HighlightAdapter?
        if (adapter != null) {
            items.value?.let {
                adapter.clearItems()
                adapter.addItems(ArrayList(it))
                if (!adapter.isLoaded) {
                    val context = recyclerView.context;
                    val controller =
                        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fade);
                    recyclerView.layoutAnimation = controller;
                    recyclerView.scheduleLayoutAnimation()
                    adapter.isLoaded = true
                }
            }
        }
    }


}