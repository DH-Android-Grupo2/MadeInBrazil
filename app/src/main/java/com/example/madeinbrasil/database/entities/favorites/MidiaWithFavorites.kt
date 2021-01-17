package com.example.madeinbrasil.database.entities.favorites

import androidx.room.Embedded
import androidx.room.Relation
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.midia.MidiaEntity

data class MidiaWithFavorites (
        @Embedded val midia: MidiaEntity,
        @Relation(
                parentColumn = "id_midia",
                entityColumn = "midiaId"
        )
        val favorites: List<Favorites>
)