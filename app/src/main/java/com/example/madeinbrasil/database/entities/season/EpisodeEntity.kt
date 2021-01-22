package com.example.madeinbrasil.database.entities.season

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeEntity (
        @PrimaryKey
        val epId: Int,
        val seasonId: Int,
        @ColumnInfo(name = "still_path")
        val stillPath: String?,
        val name: String?,
        val overview: String?
)