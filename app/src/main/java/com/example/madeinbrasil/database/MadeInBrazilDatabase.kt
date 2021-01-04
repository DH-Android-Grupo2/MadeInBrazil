package com.example.madeinbrasil.database

import android.content.Context
import androidx.room.*
import com.example.madeinbrasil.model.users.Users

object MadeInBrazilDatabase {

    @Database(entities = [Users::class], version = 2,exportSchema = false)
    abstract class MadeInBrazilRoomDatabase : RoomDatabase() {  
        abstract fun userDao(): UserDao
    }

    fun getDatabase(context: Context) : MadeInBrazilRoomDatabase{
        return Room.databaseBuilder(
            context,
            MadeInBrazilRoomDatabase::class.java,"madeInBrasil-db"
        ).build()
    }
}