package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class BR(
//        @PrimaryKey(autoGenerate = true)
//        val brId: Int,
    val flatrate: List<Flatrate>?,
    val link: String?
): Parcelable