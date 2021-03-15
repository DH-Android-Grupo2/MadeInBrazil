package com.dh.madeinbrasil.model.serieCredits

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SerieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
): Parcelable