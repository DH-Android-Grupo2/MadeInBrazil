package com.example.madeinbrasil.database.dao

import androidx.room.*
import com.example.madeinbrasil.database.entities.midia.MidiaEntity

@Dao
interface MidiaDao {
    @Query("SELECT * FROM midia")
    suspend fun getMidia(): List<MidiaEntity>

    @Query("DELETE FROM midia WHERE id_midia = :id")
    suspend fun deleteByIdMidia(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMidia(media: MidiaEntity)
}