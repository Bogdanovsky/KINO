package com.android.academy.fundamentals.ui.ui_elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.R
import com.android.academy.fundamentals.data.network.ImdbActor
//import com.android.academy.fundamentals.data.JsonMovieRepository
//import com.android.academy.fundamentals.data.Movie
import com.android.academy.fundamentals.data.network.ImdbMovie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ActorAdapter(movie: ImdbMovie) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private val actors = listOf<ImdbActor>(
        ImdbActor(1, "Mark Ruffalo", "https://i.imgur.com/DvpvklR.png"),
        ImdbActor(1, "Johny Depp", "https://i.imgur.com/DvpvklR.png"),
        ImdbActor(1, "Bred Pitt", "https://i.imgur.com/DvpvklR.png"),
        ImdbActor(1, "Jim Carrey", "https://i.imgur.com/DvpvklR.png"),
        ImdbActor(1, "Leonardo DiCaprio", "https://i.imgur.com/DvpvklR.png"),
        ImdbActor(1, "John Travolta", "https://i.imgur.com/DvpvklR.png"),
        ImdbActor(1, "Keanu Reeves", "https://i.imgur.com/DvpvklR.png")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.view_holder_actor_tv)
        private val image: ImageView = itemView.findViewById(R.id.view_holder_actor_iv)

        fun onBind(actor: ImdbActor) {
            name.text = actor.name
            Glide.with(image.context)
                .load(actor.imageUrl.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(image)
        }
    }

}



