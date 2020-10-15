package com.mayburger.dzikirqu.model


/**
 * Created by Mayburger on 10/12/18.
 */
class AyahJsonModel(
    var text_madani: String,
    var juz_number: Int,
    var chapter_id: Int,
    var verse_number: Int,
    var use_bismillah:Boolean?=false,
    var translations: ArrayList<Translations>
) {
    class Translations(
        var language_name: String,
        var text: String
    )
}

