package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.HighlightDataModel

@Dao
interface HighlightDao {

    @Insert
    fun insertHighlight(item:HighlightDataModel)

    @Query("SELECT * FROM highlight")
    suspend fun getHighlights():List<HighlightDataModel>

    @Query("DELETE FROM highlight")
    suspend fun deleteHighlights()

}