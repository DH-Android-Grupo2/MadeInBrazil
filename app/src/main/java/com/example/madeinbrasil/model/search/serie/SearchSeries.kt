package com.example.madeinbrasil.model.search.serie

import android.os.Parcelable
import com.example.madeinbrasil.model.search.Result
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchSeries(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
): Parcelable