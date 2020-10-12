package com.mayburger.dzikirqu.constants

import com.mayburger.dzikirqu.model.LinkDataModel

object Constants {
    const val BOOK_TYPE_PRAYER = 0
    const val BOOK_TYPE_QURAN = 1

    const val QURAN_AYAH_SIZE = 12472

    fun getDeepLink(): ArrayList<LinkDataModel> {
        val deepLink = ArrayList<LinkDataModel>()
        deepLink.add(LinkDataModel("Membaca Surat Al-Mulk",1,67))
        deepLink.add(LinkDataModel("Membaca Surat As-Sajdah",1,32))
        deepLink.add(LinkDataModel("Read Surah Al-Mulk",1,67))
        deepLink.add(LinkDataModel("Read Surah As-Sajdah",1,32))
        return deepLink
    }
}