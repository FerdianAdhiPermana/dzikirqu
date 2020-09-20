package com.mayburger.dzikirqu.model


/**
 * Created by Mayburger on 10/12/18.
 */
class SurahJsonModel(
    var chapter_number: Int,
    var bismillah_pre:Boolean,
    var name_simple:String,
    var name_arabic:String,
    var revelation_place:String,
    var verses_count:Int,
    var translated_name:Translated
){
    class Translated(
        var name:String
    )
}