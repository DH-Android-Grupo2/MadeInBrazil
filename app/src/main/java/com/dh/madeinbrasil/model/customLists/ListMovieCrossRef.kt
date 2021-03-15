package com.dh.madeinbrasil.model.customLists

import androidx.room.Entity

@Entity(primaryKeys = ["listId", "movieId"])
data class ListMovieCrossRef(
        val listId: Long,
        val movieId: Long
)