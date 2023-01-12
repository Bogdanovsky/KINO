package com.bogdanovsky.android.kino.ui.ui_elements

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bogdanovsky.android.kino.R
import com.bogdanovsky.android.kino.domain.Movie
import com.bogdanovsky.android.kino.ui.viewmodels.FragmentMoviesViewModel
import com.bogdanovsky.android.kino.ui.viewmodels.FragmentMoviesViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FragmentMoviesDetails() : Fragment() {


    private val viewModel: FragmentMoviesViewModel by lazy {
        Log.i("FrMoVeMo", "DetailsFragment by lazy")
        val activity = requireNotNull(this.activity){}
        ViewModelProvider(activity, FragmentMoviesViewModelFactory(activity.application))
            .get(FragmentMoviesViewModel::class.java)
    }
    private lateinit var adapter: ActorAdapter
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_movies_details, container, false)
        layout.findViewById<View>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
//        val movieId = requireArguments().getString(MOVIE_ID) // TAG
//        viewModel.setSelectedMovie(movieId!!) // TAG
        Log.i("FrMoVeMo", "DetailsFragment onCreateView $viewModel")
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = requireNotNull(viewModel.selectedMovie.value)

        val actorsRecyclerView =
            view.findViewById<RecyclerView>(R.id.fragment_movies_details_recyclerview)
        adapter = ActorAdapter(movie)
        actorsRecyclerView.adapter = adapter

        // Title
        view.findViewById<TextView>(R.id.detailsTitle).text = movie.title
        // Image
        val image = view.findViewById<ImageView>(R.id.detailsImageView)
        Glide.with(image.context)
            .load(movie.image.toUri().buildUpon().scheme("https").build())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.orig)
                    .error(R.drawable.orig)
            )
            .centerCrop()
            .into(image)
        // Full title
        view.findViewById<TextView>(R.id.tagTextView).text = movie.fullTitle
        //Reviews
        view.findViewById<TextView>(R.id.reviewsTextView).text = "${movie.imDBRating} by ${movie.imDBRatingCount} votes"
        // Crew
        view.findViewById<TextView>(R.id.crew).text = movie.crew
        // Stars
        view.findViewById<ImageView>(R.id.starIconImageView)
            .setImageResource(if (movie.imDBRating.toDouble() > 0) R.drawable.star_icon else R.drawable.star_icon_gray)
        view.findViewById<ImageView>(R.id.starIcon2ImageView)
            .setImageResource(if (movie.imDBRating.toDouble() > 2) R.drawable.star_icon else R.drawable.star_icon_gray)
        view.findViewById<ImageView>(R.id.starIcon3ImageView)
            .setImageResource(if (movie.imDBRating.toDouble() > 4) R.drawable.star_icon else R.drawable.star_icon_gray)
        view.findViewById<ImageView>(R.id.starIcon4ImageView)
            .setImageResource(if (movie.imDBRating.toDouble() > 6) R.drawable.star_icon else R.drawable.star_icon_gray)
        view.findViewById<ImageView>(R.id.starIcon5ImageView)
            .setImageResource(if (movie.imDBRating.toDouble() > 8) R.drawable.star_icon else R.drawable.star_icon_gray)

//        view.findViewById<TextView>(R.id.cast).setOnClickListener {
////            runBlocking { Log.i("IMDB ----> ", ImdbApi.retrofitService.getTop250Movies().toString()) }
//            runBlocking { viewModel.loadImdbTop250() }
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: String) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ID, movieId)
                    Log.i("FrMoVeMo", "movie.id = $movieId")
//                    putString(ARG_PARAM2, param2)
                }
            }
        private const val MOVIE_ID = "MOVIE_ID"
    }
}