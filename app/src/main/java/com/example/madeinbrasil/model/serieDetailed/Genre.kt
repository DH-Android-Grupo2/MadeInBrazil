package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
        val id: Int,
        val name: String?
): Parcelable