package com.mayburger.dzikirqu.util.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.ItemTaskBinding
import com.mayburger.dzikirqu.model.TaskDataModel
import com.mayburger.dzikirqu.model.events.UpdateTaskEvent
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.adapters.PrayerAdapter
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemPrayerViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemTaskViewModel
import com.mayburger.dzikirqu.util.rx.RxBus


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

    @BindingAdapter("tasks")
    @JvmStatic
    fun addTasks(view: ViewGroup, items:LiveData<List<TaskDataModel>>){
        view.removeAllViews()
        items.value?.let{
            for (i in it) {
                val mLayoutInflater = LayoutInflater.from(view.context)
                val binding = ItemTaskBinding.inflate(mLayoutInflater, view, false)
                binding.root.setOnClickListener {
                    if(i.taskCount < i.totalTask){
                        val task = i
                        task.taskCount += 1
                        RxBus.getDefault().send(UpdateTaskEvent(task))
                    }
                }
                binding.viewModel = ItemTaskViewModel(i)
                view.addView(binding.root)
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


}