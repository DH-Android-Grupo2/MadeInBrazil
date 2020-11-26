package com.example.madeinbrasil.model.gender
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String
): Parcelable