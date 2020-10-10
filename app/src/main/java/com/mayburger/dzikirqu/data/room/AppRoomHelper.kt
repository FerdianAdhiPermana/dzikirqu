package com.mayburger.dzikirqu.data.room

import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.db.AppDatabase
import com.mayburger.dzikirqu.model.*
import com.mayburger.dzikirqu.util.HighlightsUtil
import javax.inject.Inject

class AppRoomHelper @Inject constructor(val db: AppDatabase, val hawk: HawkHelper) : RoomHelper {

    override suspend fun getAllBooks(): List<BookDataModel> {
        return db.getBookDao().getBooks()
    }

    override suspend fun setBooks(items: List<BookDataModel>) {
        db.getBookDao().deleteAllBooks()
        db.getPrayerDao().deleteAllPrayers()
        val prayers = ArrayList<PrayerDataModel>()
        items.map { book ->
            book.prayer.map {
                it.bookId = book.id
                prayers.add(it)
            }
        }
        prayers.map {
            db.getPrayerDao().insertPrayer(it)
        }
        items.map { book ->
            db.getBookDao().insertBook(book)
        }
    }

    override suspend fun setHighlights(items: List<HighlightDataModel>) {
        items.map {
            db.getHighlightDao().insertHighlight(it)
        }
    }

    override suspend fun getHighlights(): List<HighlightDataModel> {
        if (db.getHighlightDao().getHighlights(hawk.language).isEmpty()) {
            setHighlights(HighlightsUtil.getDefaultHighlights(hawk.language))
        }
        return db.getHighlightDao().getHighlights(hawk.language)
    }

    override suspend fun insertHighlight(item: HighlightDataModel) {
        db.getHighlightDao().insertHighlight(item)
    }

    override suspend fun deleteHighlight(item: HighlightDataModel) {
        db.getHighlightDao().deleteHighlight(item)
    }

    override suspend fun setPrayers(items: List<PrayerDataModel>) {
        db.getPrayerDao().deleteAllPrayers()
        items.map { prayers ->
            db.getPrayerDao().insertPrayer(prayers)
        }
    }

    override suspend fun getBookById(id: Int): List<BookDataModel> {
        return db.getBookDao().getBookById(hawk.language, id)
    }

    override suspend fun getPrayerByBook(bookId: Int): List<PrayerDataModel> {
        return db.getPrayerDao().getPrayersByBook(hawk.language, bookId)
    }

    override suspend fun getPrayerByTitle(title: String): List<PrayerDataModel> {
        return db.getPrayerDao().getPrayerByTitle(hawk.language,"%${title}%")
    }

    override suspend fun setSurah(items: List<SurahJsonModel>, language: String) {
        items.mapIndexed { index, data ->
            db.getSurahDao().insertSurah(
                SurahDataModel(
                    data.chapter_number,
                    data.name_simple,
                    data.name_arabic,
                    data.translated_name.name,
                    data.revelation_place,
                    data.verses_count, language
                )
            )
        }
    }

    override suspend fun getSurah(): List<SurahDataModel> {
        return db.getSurahDao().getSurahs(hawk.language)
    }

    override suspend fun getSurahById(id: Int): List<SurahDataModel> {
        return db.getSurahDao().getSurahById(id, hawk.language)
    }

    override suspend fun getSurahByName(name: String): List<SurahDataModel> {
        return db.getSurahDao().getSurahByName(hawk.language,"%${name}%")
    }

    override suspend fun insertAyah(items: List<AyahJsonModel>) {
        items.map {
            db.getAyahDao().insertAyah(
                AyahDataModel(
                    it.verse_number,
                    it.juz_number,
                    it.chapter_id,
                    it.text_madani,
                    it.translations.filter { it.language_name == "english" }[0].text,
                    "en"
                )
            )
            db.getAyahDao().insertAyah(
                AyahDataModel(
                    it.verse_number,
                    it.juz_number,
                    it.chapter_id,
                    it.text_madani,
                    it.translations.filter { it.language_name == "indonesian" }[0].text,
                    "id"
                )
            )
        }
    }

    override suspend fun getAyahBySurahId(surahId: Int): List<AyahDataModel> {
        return db.getAyahDao().getAyahBySurahId(surahId, hawk.language)
    }

    override suspend fun getAyahByJuz(juz: Int): List<AyahDataModel> {
        return db.getAyahDao().getAyahByJuz(juz, hawk.language)
    }

    override suspend fun getAyahByTranslation(query: String): List<AyahDataModel> {
        return db.getAyahDao().getAyahByTranslation(hawk.language, "%${query}%")
    }

    override suspend fun getAllAyahs(): List<AyahDataModel> {
        return db.getAyahDao().getAllAyah()
    }

    override suspend fun deleteAllAyahs() {
        db.getAyahDao().deleteAllAyahs()
    }

}