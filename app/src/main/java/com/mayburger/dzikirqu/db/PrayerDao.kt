package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.PrayerDataModel

@Dao
interface PrayerDao {

    @Insert
    fun insertPrayer(item: PrayerDataModel)

    @Query("DELETE FROM prayer")
    fun deleteAllPrayers()

    @Query("SELECT * FROM prayer WHERE language LIKE :language AND book_id LIKE :bookId")
    suspend fun getPrayersByBook(language: String, bookId: Int): List<PrayerDataModel>

    @Query("SELECT * FROM prayer WHERE language LIKE :language AND title LIKE :title")
    suspend fun getPrayerByTitle(language:String,title:String): List<PrayerDataModel>
}