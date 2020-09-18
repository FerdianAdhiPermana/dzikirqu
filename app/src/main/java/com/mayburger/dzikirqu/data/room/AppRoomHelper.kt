package com.mayburger.dzikirqu.data.room

import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.db.AppDatabase
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.TaskDataModel
import com.mayburger.dzikirqu.util.TasksUtil
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AppRoomHelper @Inject constructor(val db: AppDatabase, val hawk: HawkHelper) : RoomHelper {

    override suspend fun getAllBooks(): List<BookDataModel> {
        return db.getBookDao().getBooks()
    }

    override suspend fun setBooks(items: List<BookDataModel>) {
        db.getBookDao().deleteAllBooks()
        db.getPrayerDao().deleteAllPrayers()
        val prayers = ArrayList<PrayerDataModel>()
        items.map { book ->
            book.prayer.map {
                it.bookId = book.id
                prayers.add(it)
            }
        }
        prayers.map {
            db.getPrayerDao().insertPrayer(it)
        }
        items.map { book ->
            db.getBookDao().insertBook(book)
        }
    }

    override suspend fun setTasks(items: List<TaskDataModel>) {
        items.map {
            db.getTaskDao().insertTask(it)
        }
    }

    override suspend fun getTasks(): List<TaskDataModel> {
        if (db.getTaskDao().getTaskByDate(Calendar.getInstance().time).isEmpty()) {
            setTasks(TasksUtil.getTasks())
        }
        return db.getTaskDao().getTaskByDate(Calendar.getInstance().time)
    }

    override suspend fun updateTask(task: TaskDataModel) {
        println("This is the updated task ${task.taskCount}")
        db.getTaskDao().updateTask(task)
    }

    override suspend fun setPrayers(items: List<PrayerDataModel>) {
        db.getPrayerDao().deleteAllPrayers()
        items.map { prayers ->
            db.getPrayerDao().insertPrayer(prayers)
        }
    }

    override suspend fun getBookById(id: String): List<BookDataModel> {
        return db.getBookDao().getBookById(hawk.language, id)
    }

    override suspend fun getPrayerByBookId(bookId: String): List<PrayerDataModel> {
        return db.getPrayerDao().getPrayers(hawk.language, bookId)
    }
}