package com.example.madeinbrasil.database.entities.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorites (
        @PrimaryKey
        @ColumnInfo(name = "id_favorites")
        val id: Int,
        val midiaId: Int,
        val isChecked: Boolean
)