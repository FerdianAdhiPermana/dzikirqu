package com.mayburger.dzikirqu.ui.adapters.viewmodels

import androidx.databinding.ObservableField
import com.mayburger.dzikirqu.model.TaskDataModel

class ItemTaskViewModel(val data:TaskDataModel){

    val title = ObservableField(data.title)
    var desc = ObservableField("${data.taskCount} of ${data.totalTask}")
}