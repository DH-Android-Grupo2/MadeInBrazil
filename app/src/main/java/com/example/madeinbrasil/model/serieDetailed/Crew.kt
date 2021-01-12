package com.example.madeinbrasil.model.serieDetailed

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
        @PrimaryKey
        val id: Int,
        val adult: Boolean?,
        @SerializedName("credit_id")
        @ColumnInfo(name = "credit_id")
        val creditId: String?,
        val department: String?,
        val gender: Int?,
        val job: String?,
        @SerializedName("known_for_department")
        @ColumnInfo(name = "known_for_department")
        val knownForDepartment: String?,
        val name: String?,
        @SerializedName("original_name")
        @ColumnInfo(name = "original_name")
        val originalName: String?,
        val popularity: Double?,
        @SerializedName("profile_path")
        @ColumnInfo(name = "profile_path")
        val profilePath: String?
): Parcelable