package com.mayburger.dzikirqu.di

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayburger.dzikirqu.ui.adapters.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object RecyclerModule {

    @Provides
    internal fun provideLinearLayoutManager(activity: Activity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    internal fun provideBookAdapter():BookAdapter{
        return BookAdapter()
    }
    @Provides
    internal fun provideBookListAdapter():PrayerAdapter{
        return PrayerAdapter()
    }
    @Provides
    internal fun provideHighlightAdapter():HighlightAdapter{
        return HighlightAdapter()
    }
    @Provides
    internal fun provideSurahAdapter():SurahAdapter{
        return SurahAdapter()
    }
    @Provides
    internal fun provideQuranAdapter():QuranAdapter{
        return QuranAdapter()
    }

//    @Provides
//    internal fun <T:ViewModel>provideAppAdapter(): AppAdapter<T> {
//        return AppAdapter()
//    }

}