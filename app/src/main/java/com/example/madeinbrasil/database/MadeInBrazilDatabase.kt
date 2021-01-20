package com.example.madeinbrasil.database

import android.content.Context
import androidx.room.*
import com.example.madeinbrasil.database.dao.*
import com.example.madeinbrasil.database.entities.cast.CastEntity
import com.example.madeinbrasil.database.entities.cast.MidiaPeople
import com.example.madeinbrasil.database.entities.midia.MidiaEntity
import com.example.madeinbrasil.database.entities.favorites.Favorites
import com.example.madeinbrasil.database.entities.genre.GenreEntity
import com.example.madeinbrasil.database.entities.cast.MidiaCastCrossRef
import com.example.madeinbrasil.database.entities.recommendations.RecommendationEntity
import com.example.madeinbrasil.database.entities.recommendations.RecommendationMidiaCrossRef
import com.example.madeinbrasil.database.entities.season.EpisodeEntity
import com.example.madeinbrasil.database.entities.season.SeasonEntity
import com.example.madeinbrasil.database.entities.similar.SimilarEntity
import com.example.madeinbrasil.database.entities.similar.SimilarMidiaCrossRef
import com.example.madeinbrasil.database.entities.watched.Watched
import com.example.madeinbrasil.model.search.ResultSearch
import com.example.madeinbrasil.model.upcoming.GenreConverter
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.model.users.Users

object MadeInBrazilDatabase {

    @Database(entities = [Users::class,Result::class,ResultSearch::class, GenreEntity::class,
        MidiaEntity::class, Favorites::class, Watched::class, SeasonEntity::class,
        EpisodeEntity::class, CastEntity::class, MidiaPeople::class, MidiaCastCrossRef::class,
        RecommendationEntity::class, RecommendationMidiaCrossRef::class, SimilarEntity::class,
        SimilarMidiaCrossRef::class], version = 2,exportSchema = false)
    @TypeConverters(GenreConverter::class)
    abstract class MadeInBrazilRoomDatabase : RoomDatabase() {  
        abstract fun userDao(): UserDao
        abstract fun upcomingDao(): UpcomingDao
        abstract fun discoverDao(): DiscoverTvDao
        abstract fun watchedDao(): WatchedDao
        abstract fun favoriteDao(): FavoriteDao
        abstract fun midiaDao(): MidiaDao
        abstract fun genreDao(): GenreDao
        abstract fun seasonDao(): SeasonDao
        abstract fun peopleDao(): PeopleDao
        abstract fun recommendationDao(): RecommendationDao
        abstract fun similarDao(): SimilarDao
    }

    fun getDatabase(context: Context) : MadeInBrazilRoomDatabase{
        return Room.databaseBuilder(
            context,
            MadeInBrazilRoomDatabase::class.java,"madeInBrasil-db"
        ).build()
    }
}