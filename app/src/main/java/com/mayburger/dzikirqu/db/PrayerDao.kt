package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.PrayerDataModel

@Dao
interface PrayerDao {

    @Insert
    fun insertPrayer(item:PrayerDataModel)

    @Query("DELETE FROM prayer")
    fun deleteAllPrayers()

    @Query("SELECT * FROM prayer WHERE language LIKE :language AND book_id LIKE :bookId")
    suspend fun getPrayers(language:String,bookId:Int):List<PrayerDataModel>
}