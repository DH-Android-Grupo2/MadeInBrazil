package com.example.madeinbrasil.database.dao

import androidx.room.*
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.favorites.MidiaWithFavorites

@Dao
interface FavoriteDao {
    @Query("DELETE FROM Favorites WHERE midiaId = :id")
    suspend fun deleteByIdFavorites(id: Int)

    @Transaction
    @Query("SELECT * FROM midia, favorites WHERE id_midia = id_favorites")
    suspend fun getMidiaWithFavorites(): List<MidiaWithFavorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(fav: Favorites)
}