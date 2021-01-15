package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity
data class Videos(
//        @PrimaryKey(autoGenerate = true)
//        val videoId: Int,
    val results: List<ResultXX>?
): Parcelable