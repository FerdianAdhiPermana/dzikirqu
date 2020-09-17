package com.mayburger.dzikirqu.data.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.model.PrayerDataModele
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookListViewModel
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AppFirebaseHelper @Inject constructor() : FirebaseHelper {


    private val TAG = this.javaClass.simpleName
    override suspend fun getBooks(): ArrayList<ItemBookViewModel> {
        return try {
            Firebase.firestore.collection("selection").get().await()
                .map { ItemBookViewModel(it.toObject()) }.toCollection(arrayListOf())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBookData(type: String): ArrayList<ItemBookListViewModel> {
        return try {
            var prayer = PrayerDataModele()
            val data = ArrayList<ItemBookListViewModel>()
            Firebase.firestore.collection("data-v2").document(type).get().await()?.let {
                it.toObject(PrayerDataModele::class.java)?.let {
                    prayer = it
                }
                prayer.data?.map {
                    ItemBookListViewModel(it)
                }?.toCollection(data)
            }
            data
        } catch (e: Exception) {
            throw e
        }
    }


}