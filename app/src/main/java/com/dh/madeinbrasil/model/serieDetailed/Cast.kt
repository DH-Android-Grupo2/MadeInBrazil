package com.dh.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
        val id: Int,
        val creditsId: Int?,
        val adult: Boolean?,
        val character: String?,
        @SerializedName("credit_id")
        val creditId: String?,
        val gender: Int?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        val name: String?,
        val order: Int?,
        @SerializedName("original_name")
        val originalName: String?,
        val popularity: Double?,
        @SerializedName("profile_path")
        var profilePath: String?,
        //CastPeople
        val title: String?,
        @SerializedName("original_title")
        val originalTitle: String?,
        @SerializedName("poster_path")
        var posterPath: String?
): Parcelable