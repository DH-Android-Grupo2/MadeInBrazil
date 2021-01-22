package com.example.madeinbrasil.database.entities.similar

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id_midia", "midia_id"])
data class SimilarMidiaCrossRef (
        val id_midia: Int,
        val midia_id: Int,
        @SerializedName("media_type")
        @ColumnInfo(name = "media_type")
        val midiaType: Int?,
        val name: String,
        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?
)