package com.mayburger.dzikirqu.di

import android.app.Application
import android.content.Context
import com.mayburger.dzikirqu.data.AppDataManager
import com.mayburger.dzikirqu.data.DataManager
import com.mayburger.dzikirqu.data.firebase.AppFirebaseHelper
import com.mayburger.dzikirqu.data.firebase.FirebaseHelper
import com.mayburger.dzikirqu.data.hawk.AppHawkHelper
import com.mayburger.dzikirqu.data.hawk.HawkHelper
import com.mayburger.dzikirqu.db.AppDatabase
import com.mayburger.dzikirqu.data.room.AppRoomHelper
import com.mayburger.dzikirqu.data.room.RoomHelper
import com.mayburger.dzikirqu.util.rx.AppSchedulerProvider
import com.mayburger.dzikirqu.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(context:Context): AppDatabase {
        return AppDatabase.invoke(context)
    }

    @Provides
    @Singleton
    internal fun provideRoomHelper(appRoomHelper: AppRoomHelper):RoomHelper{
        return appRoomHelper
    }

    @Provides
    @Singleton
    internal fun provideHawkHelper(appHawkHelper: AppHawkHelper): HawkHelper = appHawkHelper

    @Provides
    @Singleton
    internal fun provideFirebaseHelper(appFirebaseHelper: AppFirebaseHelper): FirebaseHelper = appFirebaseHelper

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}