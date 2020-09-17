package com.mayburger.dzikirqu.di

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.adapters.BookListAdapter
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
    internal fun provideBookListAdapter():BookListAdapter{
        return BookListAdapter()
    }

//    @Provides
//    internal fun <T:ViewModel>provideAppAdapter(): AppAdapter<T> {
//        return AppAdapter()
//    }

}