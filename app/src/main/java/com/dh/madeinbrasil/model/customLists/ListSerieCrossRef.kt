package com.dh.madeinbrasil.model.customLists

import androidx.room.Entity

@Entity(primaryKeys = ["listId", "serieId"])
data class ListSerieCrossRef(
        val listId: Long,
        val serieId: Long
)