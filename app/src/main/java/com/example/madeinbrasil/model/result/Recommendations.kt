package com.example.madeinbrasil.model.result

import android.os.Parcelable
import com.example.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recommendations(
        val page: Int,
        var results: List<Result>?,
        val total_pages: Int,
        val total_results: Int
): Parcelable