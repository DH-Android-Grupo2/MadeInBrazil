package com.dh.madeinbrasil.model.nowPlaying

import android.os.Parcelable
import com.dh.madeinbrasil.model.upcoming.Result
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NowPlaying(
    val page: Int,
    var results: List<Result>,
    val total_pages: Int,
    val total_results: Int
): Parcelable