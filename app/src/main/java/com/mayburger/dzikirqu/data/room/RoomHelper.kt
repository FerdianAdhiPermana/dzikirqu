package com.mayburger.dzikirqu.data.room

import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.PrayerDataModel


interface RoomHelper {
    suspend fun getAllBooks():List<BookDataModel>
    suspend fun getBookById(id:String):List<BookDataModel>
    suspend fun setBooks(items:List<BookDataModel>)
    suspend fun setPrayers(items:List<PrayerDataModel>)
    suspend fun getPrayerByBookId(bookId:String):List<PrayerDataModel>
}