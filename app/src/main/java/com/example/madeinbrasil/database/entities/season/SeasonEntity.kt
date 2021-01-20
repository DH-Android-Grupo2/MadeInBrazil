package com.example.madeinbrasil.database.entities.season

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class SeasonEntity (
        @PrimaryKey
        val id: Int,
        val serieId: Int,
        var name: String?,
        var overview: String?,
        @SerializedName("poster_path")
        @ColumnInfo(name = "poster_path")
        var posterPath: String?,
        @ColumnInfo(name = "season_number")
        val season_number: Int?
): Parcelable