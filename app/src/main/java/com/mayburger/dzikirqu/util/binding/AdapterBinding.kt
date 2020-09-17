package com.mayburger.dzikirqu.util.binding

import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.adapters.BookListAdapter
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookListViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel


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

    @BindingAdapter("bookListAdapter")
    @JvmStatic
    fun addBookListItems(
        recyclerView: RecyclerView,
        items: LiveData<List<ItemBookListViewModel>>
    ) {
        val adapter = recyclerView.adapter as BookListAdapter?
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