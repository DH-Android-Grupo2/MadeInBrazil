package com.example.madeinbrasil.database

import android.content.Context
import androidx.room.*
import com.example.madeinbrasil.model.customLists.*
import com.example.madeinbrasil.database.dao.*
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.search.movie.SearchResult

import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.users.Users

object MadeInBrazilDatabase {

    @Database(entities = [Users::class,Result::class,ResultSearch::class, SearchResult::class, CustomList::class,
        ListSerieItem::class, ListMovieItem::class, ListMovieCrossRef::class, ListSerieCrossRef::class],
        version = 2,exportSchema = false)

    @TypeConverters(GenreConverter::class)
    abstract class MadeInBrazilRoomDatabase : RoomDatabase() {  
        abstract fun userDao(): UserDao
        abstract fun upcomingDao(): UpcomingDao
        abstract fun discoverDao(): DiscoverTvDao
        abstract fun FilmsFragmentDao(): FilmsFragmentDao
        abstract fun customListDao(): CustomListDao
        abstract fun listMovieItemDao(): ListMovieItemDao
        abstract fun listSerieItemDao(): ListSerieItemDao
    }

    fun getDatabase(context: Context) : MadeInBrazilRoomDatabase{
        return Room.databaseBuilder(
            context,
            MadeInBrazilRoomDatabase::class.java,"madeInBrasil-db"
        ).build()
    }
}