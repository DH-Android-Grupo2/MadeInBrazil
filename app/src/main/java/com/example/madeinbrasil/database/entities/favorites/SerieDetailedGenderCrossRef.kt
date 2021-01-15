package com.example.madeinbrasil.database.entities.favorites

import androidx.room.Entity

@Entity(primaryKeys = ["serieId", "favorites_serie_id"])
data class SerieDetailedGenderCrossRef (
        val favorites_serie_id: Int,
        val serieId: Int
)