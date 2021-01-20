package com.example.madeinbrasil.database.entities.cast

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CastEntity (
        @PrimaryKey
        @ColumnInfo(name = "id_cast")
        val id: Int,
        val midiaId: Int,
//        var biography: String?,
//        var birthday: String?,
        val name: String?,
        @ColumnInfo(name = "profile_path")
        @SerializedName("profile_path")
        var profilePath: String?
): Parcelable