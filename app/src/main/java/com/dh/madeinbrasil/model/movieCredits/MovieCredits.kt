package com.dh.madeinbrasil.model.movieCredits

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCredits(
    val cast: List<Cast>?,
    val crew: List<Crew>?,
    val id: Int
): Parcelable