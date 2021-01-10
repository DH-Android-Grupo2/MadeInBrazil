package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import com.example.madeinbrasil.model.search.ResultSearch
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Similar(
        val page: Int?,
        var results: List</*ResultX*/ResultSearch>?,
        val total_pages: Int?,
        val total_results: Int?
): Parcelable