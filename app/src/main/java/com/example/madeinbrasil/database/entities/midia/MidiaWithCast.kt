package com.example.madeinbrasil.database.entities.midia

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.madeinbrasil.database.entities.cast.CastEntity
import com.example.madeinbrasil.database.entities.cast.MidiaCastCrossRef

data class MidiaWithCast (
        @Embedded val midia: MidiaEntity,
        @Relation(
                parentColumn = "id_midia",
                entityColumn = "id_cast",
                associateBy = Junction(MidiaCastCrossRef::class)
        )
        val cast: List<CastEntity>
)