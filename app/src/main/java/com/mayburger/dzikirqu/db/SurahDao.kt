package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.SurahDataModel

@Dao
interface SurahDao {

    @Insert
    suspend fun insertSurah(item:SurahDataModel)

    @Query("SELECT * FROM surah WHERE language LIKE :language")
    suspend fun getSurahs(language:String):List<SurahDataModel>

    @Query("SELECT * FROM surah WHERE id LIKE :id AND language LIKE :language")
    suspend fun getSurahById(id:Int, language:String):List<SurahDataModel>

    @Query("DELETE FROM surah")
    suspend fun deleteSurahs()
}