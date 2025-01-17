package com.dh.madeinbrasil.model.gender
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenderMovie(
    val genres: List<Genre>
): Parcelable