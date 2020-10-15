package com.mayburger.dzikirqu.di

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayburger.dzikirqu.ui.adapters.*
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemPageNumberViewModel
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
    internal fun provideBookAdapter(): BookAdapter {
        return BookAdapter()
    }

    @Provides
    internal fun providePageAdapter(): HorizontalSelectionAdapter<ItemPageNumberViewModel> {
        return HorizontalSelectionAdapter()
    }

    @Provides
    internal fun providePrayerListAdapter(): PrayerAdapter {
        return PrayerAdapter()
    }

    @Provides
    internal fun providePrayerPagerAdapter(): PrayerPagerAdapter {
        return PrayerPagerAdapter()
    }

    @Provides
    internal fun provideSurahAdapter(): SurahAdapter {
        return SurahAdapter()
    }

    @Provides
    internal fun provideJuzAdapter(): JuzAdapter {
        return JuzAdapter()
    }

    @Provides
    internal fun provideQuranBookmarkAdapter(): QuranBookmarkAdapter {
        return QuranBookmarkAdapter()
    }

    @Provides
    internal fun provideAyahAdapter(): AyahAdapter {
        return AyahAdapter()
    }

    @Provides
    internal fun provideSearchAyahAdapter(): SearchAyahAdapter {
        return SearchAyahAdapter()
    }

//    @Provides
//    internal fun <T:ViewModel>provideAppAdapter(): AppAdapter<T> {
//        return AppAdapter()
//    }

}