package com.example.madeinbrasil.database.entities.watched

import androidx.room.Embedded
import androidx.room.Relation
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.midia.MidiaEntity

data class MidiaWithWatched (
        @Embedded val midia: MidiaEntity,
        @Relation(
                parentColumn = "id_midia",
                entityColumn = "midiaId"
        )
        val watched: List<Watched>
)