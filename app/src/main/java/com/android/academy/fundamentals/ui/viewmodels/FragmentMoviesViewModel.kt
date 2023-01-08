package com.android.academy.fundamentals.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.data.database.getDatabase
import com.android.academy.fundamentals.data.repository.MovieRepository
import com.android.academy.fundamentals.domain.Movie
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
            } catch (e: Exception) {
                Log.i("REPO", "Exception at refreshMovies()")
                e.printStackTrace()
            }
        }
    }

    fun setSelectedMovie(movieId: String) {
        Log.i("FrMoVeMo", "setSelectedMovie ${movieId}")
        Log.i("FrMoVeMo", "setSelectedMovie ${movieRepository.movies.value}")
        _selectedMovie.value = movieRepository.movies.value!!.find { it.id == movieId }
        Log.i("FrMoVeMo", "setSelectedMovie ${_selectedMovie.value.toString()}")
    }
}