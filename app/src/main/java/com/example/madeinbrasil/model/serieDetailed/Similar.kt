package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madeinbrasil.model.search.ResultSearch
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class Similar(
//        @PrimaryKey(autoGenerate = true)
//        val idSimilar: Int,
//        val serieId: Int,
        val page: Int?,
        var results: List</*ResultX*/ResultSearch>?,
        val total_pages: Int?,
        val total_results: Int?
): Parcelable