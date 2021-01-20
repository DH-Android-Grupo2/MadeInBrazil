package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season(
    val air_date: String?,
    val episode_count: Int?,
    val id: Int,
    var name: String?,
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    val season_number: Int?
): Parcelable