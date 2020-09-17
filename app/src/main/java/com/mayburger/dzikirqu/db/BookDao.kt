package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.BookDataModel

@Dao
interface BookDao {

    @Insert
    fun insertBook(item:BookDataModel)

    @Query("DELETE FROM book")
    fun deleteAllBooks()

    @Query("SELECT * FROM book")
    suspend fun getBooks():List<BookDataModel>

    @Query("SELECT * FROM book WHERE language LIKE :language AND id LIKE :id")
    suspend fun getBookById(language:String,id:String):List<BookDataModel>
}