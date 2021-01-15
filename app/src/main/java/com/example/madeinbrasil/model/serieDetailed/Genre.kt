package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Genre(
        @PrimaryKey
        @ColumnInfo(name = "id_genre")
        val id: Int,
        val name: String?,
        val serieId: Int?
): Parcelable