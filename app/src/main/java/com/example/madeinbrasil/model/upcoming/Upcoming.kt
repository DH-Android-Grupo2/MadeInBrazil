package com.example.madeinbrasil.model.upcoming

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Upcoming(
    val page: Int,
    var results: List<Result>,
    val total_pages: Int,
    val total_results: Int
): Parcelable