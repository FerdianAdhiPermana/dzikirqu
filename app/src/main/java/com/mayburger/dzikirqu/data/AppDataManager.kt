package com.mayburger.dzikirqu.data

import android.content.Context
import com.google.type.LatLng
import com.mayburger.dzikirqu.data.firebase.FirebaseHelper
import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.data.room.RoomHelper
import com.mayburger.dzikirqu.model.*
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import com.mayburger.dzikirqu.util.praytimes.PrayerTimeHelper
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val mContext: Context,
    private val mHawkHelper: HawkHelper,
    private val mFirebaseHelper: FirebaseHelper,
    private val mRoomHelper: RoomHelper,
) : DataManager {

    fun getJsonStringFromRaw(rawInt: Int): String {
        val `is`: InputStream = mContext.resources.openRawResource(rawInt)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        `is`.use { `is` ->
            val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }

    override var quranLastRead: QuranLastRead?
        get() = mHawkHelper.quranLastRead
        set(value) {
            mHawkHelper.quranLastRead = value
        }

    override var quranBookmark: ArrayList<AyahDataModel>?
        get() = mHawkHelper.quranBookmark
        set(value) {
            mHawkHelper.quranBookmark = value
        }

    override suspend fun getBooks(): ArrayList<ItemBookViewModel> {
        val book = mFirebaseHelper.getBooks()
        mRoomHelper.setBooks(book.map { it.data }.toList())
        return book
    }

    override suspend fun getAllBooks(): List<BookDataModel> {
        return mRoomHelper.getAllBooks()
    }

    override suspend fun getBookById(id: Int): List<BookDataModel> {
        return mRoomHelper.getBookById(id)
    }

    override suspend fun setBooks(items: List<BookDataModel>) {
        mRoomHelper.setBooks(items)
    }

    override suspend fun setPrayers(items: List<PrayerDataModel>) {
        mRoomHelper.setPrayers(items)
    }

    override suspend fun getPrayerByBook(bookId: Int): List<PrayerDataModel> {
        return mRoomHelper.getPrayerByBook(bookId)
    }

    override suspend fun getPrayerByTitle(title: String): List<PrayerDataModel> {
        return mRoomHelper.getPrayerByTitle(title)
    }

    override suspend fun setSurah(items: List<SurahJsonModel>, language: String) {
        mRoomHelper.setSurah(items, language)
    }

    override suspend fun getSurah(): List<SurahDataModel> {
        return mRoomHelper.getSurah()
    }

    override suspend fun getSurahByName(name: String): List<SurahDataModel> {
        return mRoomHelper.getSurahByName(name)
    }

    override suspend fun getSurahById(id: Int): List<SurahDataModel> {
        return mRoomHelper.getSurahById(id)
    }

    override suspend fun insertAyah(items: List<AyahJsonModel>) {
        mRoomHelper.insertAyah(items)
    }

    override suspend fun getAllAyahs(): List<AyahDataModel> {
        return mRoomHelper.getAllAyahs()
    }

    override suspend fun getAyahBySurahId(surahId: Int): List<AyahDataModel> {
        return mRoomHelper.getAyahBySurahId(surahId)
    }

    override suspend fun getAyahByJuz(juz: Int, limit: Int): List<AyahDataModel> {
        return mRoomHelper.getAyahByJuz(juz,limit)
    }

    override suspend fun getAyahByTranslation(query: String): List<AyahDataModel> {
        return mRoomHelper.getAyahByTranslation(query)
    }

    override suspend fun deleteAllAyahs() {
        mRoomHelper.deleteAllAyahs()
    }

    override suspend fun getPrayerTime(): PrayerTime {
        return PrayerTimeHelper.getPrayerTime(mContext)
    }

    override var language: String
        get() = mHawkHelper.language
        set(value) {
            mHawkHelper.language = value
        }
    override var praytime: PrayerTime
        get() = mHawkHelper.praytime
        set(value) {
            mHawkHelper.praytime = value
        }
    override var userCoordinates: LatLng?
        get() = mHawkHelper.userCoordinates
        set(value) {
            mHawkHelper.userCoordinates = value
        }
    override var userCity: String
        get() = mHawkHelper.userCity
        set(value) {
            mHawkHelper.userCity = value
        }

    override var isQuranLoaded: Boolean
        get() = mHawkHelper.isQuranLoaded
        set(value) {
            mHawkHelper.isQuranLoaded = value
        }
}