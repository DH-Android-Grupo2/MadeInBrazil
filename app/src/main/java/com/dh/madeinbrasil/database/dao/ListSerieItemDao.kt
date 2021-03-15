package com.dh.madeinbrasil.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dh.madeinbrasil.model.customLists.ListSerieItem

@Dao
interface ListSerieItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeries(series: List<ListSerieItem>)
}