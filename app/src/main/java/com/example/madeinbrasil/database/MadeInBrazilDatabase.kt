package com.example.madeinbrasil.database

import android.content.Context
import androidx.room.*
import com.example.madeinbrasil.database.dao.*
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.users.Users

object MadeInBrazilDatabase {

    @Database(entities = [Users::class,Result::class,ResultSearch::class, GenreEntity::class,
        MidiaEntity::class, Favorites::class, Watched::class],
            version = 2,exportSchema = false)
    @TypeConverters(GenreConverter::class)
    abstract class MadeInBrazilRoomDatabase : RoomDatabase() {  
        abstract fun userDao(): UserDao
        abstract fun upcomingDao(): UpcomingDao
        abstract fun discoverDao(): DiscoverTvDao
        abstract fun watchedDao(): WatchedDao
        abstract fun favoriteMidiaDao(): FavoriteMidiaDao
    }

    fun getDatabase(context: Context) : MadeInBrazilRoomDatabase{
        return Room.databaseBuilder(
            context,
            MadeInBrazilRoomDatabase::class.java,"madeInBrasil-db"
        ).build()
    }
}