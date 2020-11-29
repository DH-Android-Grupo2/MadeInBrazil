package com.example.madeinbrasil.model.search.movie

import android.os.Parcelable
import com.example.madeinbrasil.model.upcoming.Result
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchMovie(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
): Parcelable