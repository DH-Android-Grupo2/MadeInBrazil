package com.example.madeinbrasil.database.dao

import androidx.room.*
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.favorites.MidiaWithFavorites

@Dao
interface FavoriteMidiaDao {

    @Query("SELECT * FROM midia")
    suspend fun getMidiaFavorite(): List<MidiaEntity>

    @Query("DELETE FROM Favorites WHERE midiaId = :id")
    suspend fun deleteByIdFavorites(id: Int)

    @Query("DELETE FROM midia WHERE id_midia = :id")
    suspend fun deleteByIdMidia(id: Int)

    @Transaction
    @Query("SELECT * FROM midia, favorites WHERE id_midia = id_favorites")
    suspend fun getMidiaWithFavorites(): List<MidiaWithFavorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(fav: Favorites)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMidia(media: MidiaEntity)

    @Delete
    suspend fun deleteMidia(midia: MidiaEntity)
}