package com.android.academy.fundamentals.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

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