package com.example.madeinbrasil.database.entities.midia

import androidx.room.Entity

@Entity(primaryKeys = ["id_midia","midiaId"])
data class MidiaGenreCrossRef (
        val id_midia: Int,
        val midiaId: Int
)