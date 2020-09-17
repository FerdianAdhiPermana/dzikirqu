package com.mayburger.dzikirqu.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.DataTypeConverter
import com.mayburger.dzikirqu.model.PrayerDataModel
import com.mayburger.dzikirqu.model.PrayerTypeConverter

@Database(
    entities = [BookDataModel::class, PrayerDataModel::class],
    version = 1
)
@TypeConverters(PrayerTypeConverter::class, DataTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao
    abstract fun getPrayerDao(): PrayerDao

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
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "aasagDasaB.db").build()
    }
}