package com.example.madeinbrasil.database

import android.content.Context
import androidx.room.*
import com.example.madeinbrasil.database.entities.favorites.FavoritesMovieDetailed
import com.example.madeinbrasil.database.entities.favorites.FavoritesSerieDetailed
import com.example.madeinbrasil.database.entities.favorites.SerieDetailedGenderCrossRef
import com.example.madeinbrasil.database.entities.watched.WatchedMovieDetailed
import com.example.madeinbrasil.database.entities.watched.WatchedSerieDetailed
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.serieDetailed.*
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.users.Users
import com.example.madeinbrasil.view.fragment.FavoritesFragment

object MadeInBrazilDatabase {

    @Database(entities = [Users::class,Result::class,ResultSearch::class, FavoritesSerieDetailed::class,
    Genre::class, SerieDetailedGenderCrossRef::class, FavoritesMovieDetailed::class, WatchedMovieDetailed::class,
    WatchedSerieDetailed::class],
            version = 2,exportSchema = false)
    @TypeConverters(GenreConverter::class)
    abstract class MadeInBrazilRoomDatabase : RoomDatabase() {  
        abstract fun userDao(): UserDao
        abstract fun upcomingDao(): UpcomingDao
        abstract fun discoverDao(): DiscoverTvDao
        abstract fun favoriteDao(): FavoriteDao
        abstract fun watchedDao(): WatchedDao
    }

    fun getDatabase(context: Context) : MadeInBrazilRoomDatabase{
        return Room.databaseBuilder(
            context,
            MadeInBrazilRoomDatabase::class.java,"madeInBrasil-db"
        ).build()
    }
}