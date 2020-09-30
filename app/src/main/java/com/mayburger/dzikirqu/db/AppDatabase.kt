package com.mayburger.dzikirqu.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mayburger.dzikirqu.model.*

@Database(
    entities = [BookDataModel::class, PrayerDataModel::class, HighlightDataModel::class, SurahDataModel::class, AyahDataModel::class],
    version = 2
)
@TypeConverters(
    ListPrayerTypeConverter::class,
    PrayerDataTypeConverter::class,
    PrayerTypeConverter::class,
    SurahTypeConverter::class,
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBookDao(): BookDao
    abstract fun getPrayerDao(): PrayerDao
    abstract fun getSurahDao(): SurahDao
    abstract fun getAyahDao(): AyahDao
    abstract fun getHighlightDao(): HighlightDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "hellopewds.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}