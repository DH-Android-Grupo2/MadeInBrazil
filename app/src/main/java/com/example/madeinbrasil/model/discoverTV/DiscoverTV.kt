package com.example.madeinbrasil.model.discoverTV

import android.os.Parcelable
import com.example.madeinbrasil.model.search.ResultSearch

import kotlinx.android.parcel.Parcelize


@Parcelize
data class DiscoverTV(
    val page: Int,
    var results: List<ResultSearch>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable