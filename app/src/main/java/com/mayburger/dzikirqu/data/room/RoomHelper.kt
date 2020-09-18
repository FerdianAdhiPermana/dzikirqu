package com.mayburger.dzikirqu.data.room

import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.TaskDataModel


interface RoomHelper {
    suspend fun getAllBooks():List<BookDataModel>
    suspend fun getBookById(id:String):List<BookDataModel>
    suspend fun setBooks(items:List<BookDataModel>)

    suspend fun setTasks(items:List<TaskDataModel>)
    suspend fun getTasks():List<TaskDataModel>
    suspend fun updateTask(task:TaskDataModel)

    suspend fun setPrayers(items:List<PrayerDataModel>)
    suspend fun getPrayerByBookId(bookId:String):List<PrayerDataModel>
}