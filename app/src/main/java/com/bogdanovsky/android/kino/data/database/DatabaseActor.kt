package com.bogdanovsky.android.kino.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdanovsky.android.kino.domain.Actor


@Entity
data class DatabaseActor (
    @PrimaryKey
    val id: String,
    val name: String,
    val imageUrl: String
)

//fun DatabaseActor.toActor(): Actor {
//    return Actor(
//        id = this.id,
//        name = this.name,
//        imageUrl = this.imageUrl
//    )
//}
//
//fun List<DatabaseActor>.toActorList(): List<Actor> {
//    return map {
//        Actor(
//            id = it.id,
//            name = it.name,
//            imageUrl = it.imageUrl
//        )
//    }
//}