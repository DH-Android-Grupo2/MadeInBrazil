package com.example.madeinbrasil.database

import android.content.Context
import androidx.room.*
import com.example.madeinbrasil.model.users.Users

object MadeInBrazilDatabase {

    @Database(entities = [Users::class], version = 1,exportSchema = false)
    abstract class MadeInBrazilRoomDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
    }

    fun getDatabase(context: Context) : RoomDatabase{
        return Room.databaseBuilder(
            context,
            MadeInBrazilRoomDatabase::class.java,"madeInBrazil-db"
        ).build()
    }
}