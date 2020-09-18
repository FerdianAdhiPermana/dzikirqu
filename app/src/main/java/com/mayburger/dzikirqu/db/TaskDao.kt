package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mayburger.dzikirqu.model.TaskDataModel
import java.util.*

@Dao
interface TaskDao {

    @Insert
    fun insertTask(item:TaskDataModel)

    @Query("SELECT * FROM task WHERE date LIKE :date")
    suspend fun getTaskByDate(date:Date):List<TaskDataModel>

    @Update
    suspend fun updateTask(item:TaskDataModel)

}