package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Similar(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
): Parcelable