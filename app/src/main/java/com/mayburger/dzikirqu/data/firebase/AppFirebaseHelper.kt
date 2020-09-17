package com.mayburger.dzikirqu.data.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AppFirebaseHelper @Inject constructor() : FirebaseHelper {

    private val TAG = this.javaClass.simpleName
    override suspend fun getBooks(): ArrayList<ItemBookViewModel> {
        return try {
            Firebase.firestore.collection("books").get().await()
                .map { ItemBookViewModel(it.toObject()) }.toCollection(arrayListOf())
        } catch (e: Exception) {
            throw e
        }
    }
}