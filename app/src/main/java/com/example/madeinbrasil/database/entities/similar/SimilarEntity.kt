package com.example.madeinbrasil.database.entities.similar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SimilarEntity (
        @PrimaryKey
        @ColumnInfo(name = "midia_id")
        val midiaid: Int,
        @ColumnInfo(name = "media_type")
        val mediaType: Int?,
        val name: String,
        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        var posterPath: String?
)