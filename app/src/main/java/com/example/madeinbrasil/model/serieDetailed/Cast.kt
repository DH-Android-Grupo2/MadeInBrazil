package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Cast(
        @PrimaryKey
        val id: Int,
        val adult: Boolean?,
        val character: String?,
        @SerializedName("credit_id")
        @ColumnInfo(name = "credit_id")
        val creditId: String?,
        val gender: Int?,
        @SerializedName("known_for_department")
        @ColumnInfo(name = "known_for_department")
        val knownForDepartment: String?,
        val name: String?,
        val order: Int?,
        @SerializedName("original_name")
        @ColumnInfo(name = "original_name")
        val originalName: String?,
        val popularity: Double?,
        @SerializedName("profile_path")
        @ColumnInfo(name = "profile_path")
        var profilePath: String?,
        //CastPeople
        val title: String?,
        @SerializedName("original_title")
        @ColumnInfo(name = "original_title")
        val originalTitle: String?,
        @SerializedName("poster_path")
        @ColumnInfo(name = "poster_path")
        var posterPath: String?
): Parcelable