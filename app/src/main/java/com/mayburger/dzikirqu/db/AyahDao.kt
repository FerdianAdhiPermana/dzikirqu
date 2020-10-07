package com.mayburger.dzikirqu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayburger.dzikirqu.model.AyahDataModel

@Dao
interface AyahDao {

    @Insert
    suspend fun insertAyah(item:AyahDataModel)

    @Query("SELECT * FROM ayah WHERE surahId LIKE :surahId AND language LIKE :language ORDER BY id ASC")
    suspend fun getAyahBySurahId(surahId:Int,language:String):List<AyahDataModel>
    @Query("SELECT * FROM ayah WHERE juz LIKE :juz AND language LIKE :language ORDER BY surahId ASC")
    suspend fun getAyahByJuz(juz:Int,language:String):List<AyahDataModel>
    @Query("SELECT * FROM ayah")
    suspend fun getAllAyah():List<AyahDataModel>
    @Query("DELETE FROM ayah")
    suspend fun deleteAllAyahs()

    @Query("SELECT * FROM ayah WHERE language LIKE :language AND translation LIKE :query")
    suspend fun getAyahByTranslation(language:String, query:String):List<AyahDataModel>
}