package com.example.madeinbrasil.model.upcoming

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class GenreConverter {

        @TypeConverter
        fun gettingListFromString(genreIds: String): List<Int> {
            val list = mutableListOf<Int>()

            val array = genreIds.split(",".toRegex()).dropLastWhile {
                it.isEmpty()
            }.toTypedArray()

            for (s in array) {
                if (s.isNotEmpty()) {
                    list.add(s.toInt())
                }
            }
            return list
        }

        @TypeConverter
        fun writingStringFromList(list: List<Int>): String {
            var genreIds = ""
            for (i in list) genreIds += ",$i"
            return genreIds
        }


    @TypeConverter
    fun listToJson(value: List<String>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {

        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>?
        val list = objects?.toList()
        return list
    }


  @TypeConverter
  fun listGenreToJson(value: List<Genre>) : String {

      return Gson().toJson(value)
  }


    fun jsonToListGenre(value: String) : List<Genre> {

        val objects = Gson().fromJson(value, Array<Genre>::class.java) as Array<Genre>
        val list = objects.toList()
        return list
    }


    }


