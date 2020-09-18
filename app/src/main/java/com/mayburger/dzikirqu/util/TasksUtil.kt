package com.mayburger.dzikirqu.util

import com.mayburger.dzikirqu.model.TaskDataModel
import java.util.*
import kotlin.collections.ArrayList

object TasksUtil {
    fun getTasks():ArrayList<TaskDataModel>{
        val tasks = ArrayList<TaskDataModel>()
        tasks.add(TaskDataModel("Read Morning Dzikir",1,0, true, Calendar.getInstance().time))
        tasks.add(TaskDataModel("Read Evening Dzikir",1,0, true, Calendar.getInstance().time))
        tasks.add(TaskDataModel("Read Dzikir after Shalat",5,0, true, Calendar.getInstance().time))
        return tasks
    }
}