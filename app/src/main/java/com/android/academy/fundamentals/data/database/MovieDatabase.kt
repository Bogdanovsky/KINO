package com.android.academy.fundamentals.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
   abstract val mdbDao : MovieDatabaseDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context) : MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movies"
            ).build()
        }
    }
    return INSTANCE
}


