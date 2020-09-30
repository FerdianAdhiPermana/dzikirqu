package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.HighlightDataModel

@Dao
interface HighlightDao {

    @Insert
    fun insertHighlight(item:HighlightDataModel)

    @Query("SELECT * FROM highlight WHERE language LIKE :language")
    suspend fun getHighlights(language:String):List<HighlightDataModel>

    @Delete
    suspend fun deleteHighlight(highlight:HighlightDataModel)

}