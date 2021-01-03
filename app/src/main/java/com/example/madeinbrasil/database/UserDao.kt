package com.example.madeinbrasil.database

import androidx.room.*
import com.example.madeinbrasil.model.users.Users

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserbyId(userId:Int): List<Users>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAllUsers(users: List<Users>)

    @Delete
    suspend fun deleteUser(user:Users)

}