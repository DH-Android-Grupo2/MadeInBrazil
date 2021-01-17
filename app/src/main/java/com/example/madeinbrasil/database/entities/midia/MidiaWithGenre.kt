package com.example.madeinbrasil.database.entities.midia

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.madeinbrasil.database.entities.genre.GenreEntity

data class MidiaWithGenre (
        @Embedded val midia: MidiaEntity,
        @Relation(
                parentColumn = "id_midia",
                entityColumn = "midiaId",
                associateBy = Junction(MidiaGenreCrossRef::class)
        )
        val genre: List<GenreEntity>
)