package com.example.madeinbrasil.database.entities.season

import androidx.room.Embedded
import androidx.room.Relation
import com.example.madeinbrasil.database.entities.midia.MidiaEntity

data class SeasonWithMidia (
        @Embedded val midia: MidiaEntity,
        @Relation(
                parentColumn = "id_midia",
                entityColumn = "serieId"
        )
        val season: List<SeasonEntity>
)