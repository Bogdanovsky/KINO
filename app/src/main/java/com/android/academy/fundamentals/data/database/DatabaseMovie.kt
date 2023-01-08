package com.android.academy.fundamentals.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.academy.fundamentals.domain.Movie

@Entity
data class DatabaseMovie (
    @PrimaryKey
    val id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDBRating: String,
    val imDBRatingCount: String)


fun List<DatabaseMovie>.toMovieList(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            rank = it.rank,
            title = it.title,
            fullTitle = it.fullTitle,
            year = it.year,
            image = it.image,
            crew = it.crew,
            imDBRating = it.imDBRating,
            imDBRatingCount = it.imDBRatingCount
        )
    }
}