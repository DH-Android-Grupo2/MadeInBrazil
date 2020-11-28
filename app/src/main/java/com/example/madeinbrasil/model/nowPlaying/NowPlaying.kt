package com.example.madeinbrasil.model.nowPlaying

import android.os.Parcelable
import com.example.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NowPlaying(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
): Parcelable