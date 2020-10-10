package com.mayburger.dzikirqu.data.room

import com.mayburger.dzikirqu.model.*


interface RoomHelper {
    suspend fun getAllBooks():List<BookDataModel>
    suspend fun getBookById(id:Int):List<BookDataModel>
    suspend fun setBooks(items:List<BookDataModel>)

    suspend fun setHighlights(items:List<HighlightDataModel>)
    suspend fun insertHighlight(item:HighlightDataModel)
    suspend fun getHighlights():List<HighlightDataModel>
    suspend fun deleteHighlight(item:HighlightDataModel)

    suspend fun setPrayers(items:List<PrayerDataModel>)
    suspend fun getPrayerByBook(bookId:Int):List<PrayerDataModel>
    suspend fun getPrayerByTitle(title:String):List<PrayerDataModel>

    suspend fun setSurah(items:List<SurahJsonModel>,language:String)
    suspend fun getSurah():List<SurahDataModel>
    suspend fun getSurahById(id:Int):List<SurahDataModel>
    suspend fun getSurahByName(name:String):List<SurahDataModel>

    suspend fun insertAyah(items:List<AyahJsonModel>)
    suspend fun getAyahBySurahId(surahId:Int):List<AyahDataModel>
    suspend fun getAyahByJuz(juz:Int):List<AyahDataModel>
    suspend fun getAyahByTranslation(query:String):List<AyahDataModel>
    suspend fun getAllAyahs():List<AyahDataModel>
    suspend fun deleteAllAyahs()

}