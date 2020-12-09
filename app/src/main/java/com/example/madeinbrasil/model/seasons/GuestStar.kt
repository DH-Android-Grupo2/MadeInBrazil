package com.example.madeinbrasil.model.seasons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuestStar(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
): Parcelable