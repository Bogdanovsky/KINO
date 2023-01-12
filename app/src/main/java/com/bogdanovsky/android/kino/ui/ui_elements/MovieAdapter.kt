package com.bogdanovsky.android.kino.ui.ui_elements

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bogdanovsky.android.kino.R
import com.bogdanovsky.android.kino.domain.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter(
    private val onItemClickListener: OnItemClickListener,
    context: Context
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val context = context
    private var movies: List<Movie> = listOf()
    fun setMovies(value: List<Movie>) {
        movies = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view, onItemClickListener)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position], context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    interface OnItemClickListener {
        fun onItemClicked(movieId: String)
    }

    class MovieViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.movie_title_tv)
        private val poster: ImageView = itemView.findViewById(R.id.poster_iv)
        private val reviews: TextView = itemView.findViewById(R.id.reviews_tv)
        private val duration: TextView = itemView.findViewById(R.id.year)
        private val starIcon1: ImageView = itemView.findViewById(R.id.starIcon_iv)
        private val starIcon2: ImageView = itemView.findViewById(R.id.starIcon2_iv)
        private val starIcon3: ImageView = itemView.findViewById(R.id.starIcon3_iv)
        private val starIcon4: ImageView = itemView.findViewById(R.id.starIcon4_tv)
        private val starIcon5: ImageView = itemView.findViewById(R.id.starIcon5_iv)


        fun onBind(movie: Movie, context: Context) {
            title.text = movie.title
            Glide.with(context)
                .load(movie.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                    .placeholder(R.drawable.avengers_poster)
                    .error(R.drawable.black_widow_poster))
                .into(poster)
            reviews.text = "${movie.imDBRating} by ${movie.imDBRatingCount} votes"
            duration.text = "${movie.year}"
            starIcon1.setImageResource(if (movie.imDBRating.toDouble() > 0) R.drawable.star_icon else R.drawable.star_icon_gray)
            starIcon2.setImageResource(if (movie.imDBRating.toDouble() > 2) R.drawable.star_icon else R.drawable.star_icon_gray)
            starIcon3.setImageResource(if (movie.imDBRating.toDouble() > 4) R.drawable.star_icon else R.drawable.star_icon_gray)
            starIcon4.setImageResource(if (movie.imDBRating.toDouble() > 6) R.drawable.star_icon else R.drawable.star_icon_gray)
            starIcon5.setImageResource(if (movie.imDBRating.toDouble() > 8) R.drawable.star_icon else R.drawable.star_icon_gray)

            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(movie.id)
            }
        }
    }
}