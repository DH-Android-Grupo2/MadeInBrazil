package com.dh.madeinbrasil.model.customLists.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dh.madeinbrasil.model.customLists.*

data class ListWithMedia(
        @Embedded val list: CustomList,
        @Relation(
                parentColumn = "listId",
                entityColumn = "movieId",
                associateBy = Junction(ListMovieCrossRef::class)
        )
        val movies: List<ListMovieItem>,
        @Relation(
                parentColumn = "listId",
                entityColumn = "serieId",
                associateBy = Junction(ListSerieCrossRef::class)
        )
        val series: List<ListSerieItem>
)