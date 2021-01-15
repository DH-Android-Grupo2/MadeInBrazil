package com.example.madeinbrasil.database.entities.favorites

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.madeinbrasil.model.serieDetailed.Genre
import com.example.madeinbrasil.model.serieDetailed.SerieDetailed

data class SerieDetailedWithGenres (
        @Embedded val serieDetailed: FavoritesSerieDetailed,
        @Relation(
                parentColumn = "favorites_serie_id",
                entityColumn = "serieId",
                associateBy = Junction(SerieDetailedGenderCrossRef::class)
        )
        val genre: List<Genre>
)