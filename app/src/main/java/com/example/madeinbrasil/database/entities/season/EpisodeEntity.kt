package com.example.madeinbrasil.database.entities.season

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class EpisodeEntity (
        @ColumnInfo(name = "still_path")
        val stillPath: String?,
        val name: String?,
        val overview: String?
)