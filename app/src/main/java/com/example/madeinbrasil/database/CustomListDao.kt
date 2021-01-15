package com.example.madeinbrasil.database

import androidx.room.*
import com.example.madeinbrasil.model.customLists.CustomList
import com.example.madeinbrasil.model.customLists.ListMovieCrossRef
import com.example.madeinbrasil.model.customLists.ListSerieCrossRef
import com.example.madeinbrasil.model.customLists.relation.ListWithMedia

@Dao
interface CustomListDao {

    @Transaction
    @Query("SELECT * from custom_lists")
    suspend fun getListWithMedia(): List<ListWithMedia>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createList(list: CustomList): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCustomListCrossrefMovie(list: List<ListMovieCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCustomListCrossrefSerie(list: List<ListSerieCrossRef>)
}