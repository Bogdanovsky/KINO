package com.bogdanovsky.android.kino.ui.ui_elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bogdanovsky.android.kino.R
import com.bogdanovsky.android.kino.data.database.MovieDatabase
import com.bogdanovsky.android.kino.data.database.getDatabase
import com.bogdanovsky.android.kino.data.network.ImdbApi
import com.bogdanovsky.android.kino.data.network.ImdbApiService
import com.bogdanovsky.android.kino.data.network.toActorList
import com.bogdanovsky.android.kino.data.repository.MovieRepository
import com.bogdanovsky.android.kino.domain.Actor
import com.bogdanovsky.android.kino.domain.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.runBlocking

class ActorAdapter(movie: Movie) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private val actors: List<Actor> = runBlocking {
        ImdbApi.retrofitService.getFullCast(movie.id).actors.toActorList()
    }

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

        fun onBind(actor: Actor) {
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



