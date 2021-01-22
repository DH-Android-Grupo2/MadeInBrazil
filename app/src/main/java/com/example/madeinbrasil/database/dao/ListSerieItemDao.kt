package com.example.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.madeinbrasil.model.customLists.ListSerieItem

@Dao
interface ListSerieItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeries(series: List<ListSerieItem>)
}