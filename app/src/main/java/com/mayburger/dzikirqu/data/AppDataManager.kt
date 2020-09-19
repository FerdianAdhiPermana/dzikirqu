package com.mayburger.dzikirqu.data

import android.content.Context
import com.google.type.LatLng
import com.mayburger.dzikirqu.data.firebase.FirebaseHelper
import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.data.room.RoomHelper
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.HighlightDataModel
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.PrayerTime
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

    override suspend fun setHighlights(items: List<HighlightDataModel>) {
        mRoomHelper.setHighlights(items)
    }

    override suspend fun getHighlights(): List<HighlightDataModel> {
        return mRoomHelper.getHighlights()
    }

    override suspend fun setBooks(items: List<BookDataModel>) {
        mRoomHelper.setBooks(items)
    }

    override suspend fun setPrayers(items: List<PrayerDataModel>) {
        mRoomHelper.setPrayers(items)
    }

    override suspend fun getPrayerByBookId(bookId: Int): List<PrayerDataModel> {
        return mRoomHelper.getPrayerByBookId(bookId)
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
}