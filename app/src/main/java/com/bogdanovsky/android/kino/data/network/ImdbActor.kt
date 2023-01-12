package com.bogdanovsky.android.kino.data.network

import com.bogdanovsky.android.kino.domain.Actor
import kotlinx.serialization.Serializable

//@Serializable
//data class ImdbActor (
//    val id: Int,
//    val name: String,
//    val imageUrl: String
//)

fun List<ImdbActor>.toActorList() : List<Actor> {
    return this.map { Actor(
        it.id,
        it.name,
        it.image
    ) }
}