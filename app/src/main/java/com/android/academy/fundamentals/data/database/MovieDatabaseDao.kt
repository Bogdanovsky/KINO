package com.android.academy.fundamentals.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.academy.fundamentals.data.network.ImdbMovie

@Dao
interface MovieDatabaseDao {

    @Query("select * from databasemovie")
    fun getMovies(): List<ImdbMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<ImdbMovie>)

}