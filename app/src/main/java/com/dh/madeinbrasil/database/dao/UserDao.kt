package com.dh.madeinbrasil.database.dao

import androidx.room.*
import com.dh.madeinbrasil.model.users.Users

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserbyId(userId:Int): List<Users>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user:Users)

    @Query("SELECT * FROM users WHERE email = (:userEmail) AND password = (:userPass) LIMIT 1")
    suspend fun login(userEmail:String,userPass:String): Users

    @Delete
    suspend fun deleteUser(user:Users)


}