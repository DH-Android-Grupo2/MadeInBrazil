package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import com.example.madeinbrasil.model.search.ResultSearch
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recommendations(
        val page: Int?,
        var results: List<ResultSearch/*Result*/>?,
        val total_pages: Int?,
        val total_results: Int?
): Parcelable