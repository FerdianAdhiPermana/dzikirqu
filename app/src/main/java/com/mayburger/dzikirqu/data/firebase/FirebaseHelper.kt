package com.mayburger.dzikirqu.data.firebase

import com.mayburger.dzikirqu.ui.adapters.viewmodels.ItemBookViewModel


interface FirebaseHelper {
    suspend fun getBooks():ArrayList<ItemBookViewModel>
}