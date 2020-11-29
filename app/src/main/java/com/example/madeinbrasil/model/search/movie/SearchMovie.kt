package com.example.madeinbrasil.model.search.movie

import android.os.Parcelable
import com.example.madeinbrasil.model.upcoming.Result
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchMovie(
<<<<<<< HEAD
        val page: Int,
        var results: List<Result>,
        @SerializedName("total_pages")
=======
    val page: Int,
    var results: List<Result>,
    @SerializedName("total_pages")
>>>>>>> 43aedfac5808257d3f13b0692cca9510b202bd9d
    val totalPages: Int,
        @SerializedName("total_results")
    val totalResults: Int
): Parcelable