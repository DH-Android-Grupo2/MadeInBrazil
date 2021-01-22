package com.example.madeinbrasil.database.dao

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

    @Transaction
    @Query("SELECT * from custom_lists WHERE listId = :id")
    suspend fun getListUni(id: Long): ListWithMedia

    @Query("SELECT * from custom_lists")
    suspend fun getCustomLists(): List<CustomList>

    @Delete
    suspend fun deleteMoviesFromList(items: List<ListMovieCrossRef>)

    @Delete
    suspend fun deleteSeriesFromList(items: List<ListSerieCrossRef>)
}