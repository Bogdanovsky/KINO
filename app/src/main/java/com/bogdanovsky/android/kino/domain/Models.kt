package com.bogdanovsky.android.kino.domain

data class Movie (
    val id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDBRating: String,
    val imDBRatingCount: String
)

data class Actor (
    val id: String,
    val name: String,
    val imageUrl: String
)