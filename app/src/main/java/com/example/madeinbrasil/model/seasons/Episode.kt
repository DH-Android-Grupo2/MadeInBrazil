package com.example.madeinbrasil.model.seasons

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    @SerializedName("air_date")
    val airDate: String?,
    val crew: List<Crew>?,
    val episode_number: Int?,
    val guest_stars: List<GuestStar>?,
    val id: Int,
    var name: String?,
    var overview: String?,
    val production_code: String,
    val season_number: Int?,
    var still_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
): Parcelable