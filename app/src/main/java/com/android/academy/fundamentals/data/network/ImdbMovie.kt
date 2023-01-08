package com.android.academy.fundamentals.data.network

import com.android.academy.fundamentals.data.database.DatabaseMovie
import com.android.academy.fundamentals.domain.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImdbMovie (
    val id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    @SerialName("imDbRating")
    val imDBRating: String,
    @SerialName("imDbRatingCount")
    val imDBRatingCount: String
)

fun List<ImdbMovie>.toDatabaseMovieList() : List<DatabaseMovie> {
    return this.map { DatabaseMovie(
        it.id,
        it.rank,
        it.title,
        it.fullTitle,
        it.year,
        it.image,
        it.crew,
        it.imDBRating,
        it.imDBRatingCount
    ) }
}