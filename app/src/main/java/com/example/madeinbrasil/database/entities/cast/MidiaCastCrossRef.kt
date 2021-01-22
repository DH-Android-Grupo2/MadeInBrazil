package com.example.madeinbrasil.database.entities.cast

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id_midia", "id_cast"])
data class MidiaCastCrossRef (
        val id_midia: Int,
        val id_cast: Int,
        val name: String?,
        @ColumnInfo(name = "profile_path")
        @SerializedName("profile_path")
        var profilePath: String?,
        val order: Int?
)