package com.example.madeinbrasil.database.entities.watched

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Watched (
        @PrimaryKey
        @ColumnInfo(name = "id_watched")
        val id: Int,
        val midiaId: Int,
        val isChecked: Boolean
)