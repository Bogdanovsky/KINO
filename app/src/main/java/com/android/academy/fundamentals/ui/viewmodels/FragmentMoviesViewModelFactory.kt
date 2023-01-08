package com.android.academy.fundamentals.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FragmentMoviesViewModelFactory(val app: Application) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        FragmentMoviesViewModel::class.java -> FragmentMoviesViewModel(app)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}