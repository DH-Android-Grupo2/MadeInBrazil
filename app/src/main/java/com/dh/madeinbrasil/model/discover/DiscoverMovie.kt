package com.dh.madeinbrasil.model.discover

import android.os.Parcelable
import com.dh.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DiscoverMovie(
        val page: Int,
        var results: List<Result>,
        val total_pages: Int,
        val total_results: Int
) : Parcelable