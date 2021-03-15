package com.dh.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dh.madeinbrasil.model.search.ResultSearch


@Dao
interface DiscoverTvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscover(tv: List<ResultSearch>)


    @Query("SELECT * FROM home_discover WHERE type == 0")
    suspend fun getDiscover() : List<ResultSearch>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchTV(tv: List<ResultSearch>)


    @Query("SELECT * FROM home_discover WHERE type == 1 AND name LIKE :query ")
    suspend fun getSearchQueryTV(query : String) : List<ResultSearch>



}