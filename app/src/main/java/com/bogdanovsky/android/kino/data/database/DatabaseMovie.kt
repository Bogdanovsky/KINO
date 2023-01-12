package com.bogdanovsky.android.kino.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdanovsky.android.kino.domain.Movie

@Entity
data class DatabaseMovie(
    @PrimaryKey
    val id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDBRating: String,
    val imDBRatingCount: String,
)

fun DatabaseMovie.toMovie(): Movie {
    return Movie(
        id = this.id,
        rank = this.rank,
        title = this.title,
        fullTitle = this.fullTitle,
        year = this.year,
        image = this.image,
        crew = this.crew,
        imDBRating = this.imDBRating,
        imDBRatingCount = this.imDBRatingCount
    )
}

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