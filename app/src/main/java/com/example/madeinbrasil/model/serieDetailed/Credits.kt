package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.example.madeinbrasil.model.movieCredits.Cast
@Parcelize
data class Credits(
    val cast: List<Cast>?,
    val crew: List<Crew>?
): Parcelable