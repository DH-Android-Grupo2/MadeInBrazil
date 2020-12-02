package com.example.madeinbrasil.model.discover

import android.os.Parcelable
import com.example.madeinbrasil.model.search.ResultSearch
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoverMovie(
        val page: Int,
        @SerializedName("result_discover_movies")
        val resultDiscoverMovies: List<ResultSearch>,
        @SerializedName("total_pages")
    val totalPages: Int,
        @SerializedName("total_results")
    val totalResults: Int
) : Parcelable {}
