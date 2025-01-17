package com.dh.madeinbrasil.model.serieCredits

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    val adult: Boolean,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int?,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    var profilePath: String?
): Parcelable {}