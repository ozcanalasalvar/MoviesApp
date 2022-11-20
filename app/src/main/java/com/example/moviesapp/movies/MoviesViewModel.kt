package com.example.moviesapp.movies

import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
}