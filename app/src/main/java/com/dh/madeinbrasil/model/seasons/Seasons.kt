package com.dh.madeinbrasil.model.seasons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seasons(
    val _id: String,
    val air_date: String?,
    val episodes: List<Episode>?,
    val id: Int,
    val name: String?,
    var overview: String?,
    var poster_path: String?,
    val season_number: Int?
): Parcelable