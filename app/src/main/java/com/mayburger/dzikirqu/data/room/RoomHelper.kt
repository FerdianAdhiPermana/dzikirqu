package com.mayburger.dzikirqu.data.room

import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.HighlightDataModel
import com.mayburger.dzikirqu.model.PrayerDataModel


interface RoomHelper {
    suspend fun getAllBooks():List<BookDataModel>
    suspend fun getBookById(id:Int):List<BookDataModel>
    suspend fun setBooks(items:List<BookDataModel>)

    suspend fun setHighlights(items:List<HighlightDataModel>)
    suspend fun getHighlights():List<HighlightDataModel>

    suspend fun setPrayers(items:List<PrayerDataModel>)
    suspend fun getPrayerByBookId(bookId:Int):List<PrayerDataModel>
}