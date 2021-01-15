package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultXX(
        val id: String,
        val videoIdResult: Int,
        val iso_3166_1: String?,
        val iso_639_1: String?,
        val key: String?,
        val name: String?,
        val site: String?,
        val size: Int?,
        val type: String?
): Parcelable