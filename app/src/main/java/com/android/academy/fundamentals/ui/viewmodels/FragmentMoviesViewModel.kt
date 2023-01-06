package com.android.academy.fundamentals.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.android.academy.fundamentals.data.JsonMovieRepository
import com.android.academy.fundamentals.data.network.ImdbApi
import com.android.academy.fundamentals.data.network.ImdbMovie
import kotlinx.coroutines.*

class FragmentMoviesViewModel() : ViewModel() {

    private val _movies: MutableLiveData<List<ImdbMovie>> = MutableLiveData(listOf())
    val movies: LiveData<List<ImdbMovie>> = _movies

    private val _selectedMovie: MutableLiveData<ImdbMovie> = MutableLiveData()
    val selectedMovie: LiveData<ImdbMovie> = _selectedMovie

    init {
        Log.i("TAG1", "init block in VM start")
        loadImdbTop250()
    }

    private fun loadImdbTop250() {
        viewModelScope.launch {
            try {
                Log.i("API", ImdbApi.retrofitService.getTop250Movies().toString())
                _movies.value = ImdbApi.retrofitService.getTop250Movies().items
            } catch (e: Exception) {
                Log.i("API", "ERRORRRRRR!!!!")
                e.printStackTrace()
            }
        }
    }

    fun setSelectedMovie(movieId: String) {
        _selectedMovie.value = movies.value!!.find { it.id == movieId }
    }



}