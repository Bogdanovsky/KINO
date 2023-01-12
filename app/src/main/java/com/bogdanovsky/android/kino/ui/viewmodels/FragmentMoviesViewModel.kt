package com.bogdanovsky.android.kino.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdanovsky.android.kino.data.database.getDatabase
import com.bogdanovsky.android.kino.data.repository.MovieRepository
import com.bogdanovsky.android.kino.domain.Movie
import kotlinx.coroutines.*

class FragmentMoviesViewModel(app: Application) : ViewModel() {

    private val movieDatabase = getDatabase(app)
    private val movieRepository = MovieRepository(movieDatabase)

    val movies = movieRepository.movies

    private val _selectedMovie: MutableLiveData<Movie> = MutableLiveData()
    val selectedMovie: LiveData<Movie> = _selectedMovie

    init {
        viewModelScope.launch {
            try {
                movieRepository.refreshMovies()
                Log.i("FrMoVeMo", "movieRepository refreshed")
            } catch (e: Exception) {
                Log.i("REPO", "Exception at refreshMovies()")
                e.printStackTrace()
            }
        }
    }

    fun setSelectedMovie(movieId: String) {
//        viewModelScope.launch {
            try {
                _selectedMovie.value = movieRepository.getMovieById(movieId)
            } catch (e: Exception) {
                Log.i("FrMoVeMo", "Exception at setSelectedMovie()")
                e.printStackTrace()
            }
//        }
    }
}