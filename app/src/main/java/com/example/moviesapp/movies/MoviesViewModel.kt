package com.example.moviesapp.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapp.data.Movie
import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> = _movies

    var pagingDataFlow: Flow<PagingData<Movie>>? = null

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() = viewModelScope.launch {
        if (pagingDataFlow != null)
            pagingDataFlow
        else
            pagingDataFlow = repository.getNowPlayingMovies().cachedIn(viewModelScope)
    }

    init {
        getTrendMovies()
        getNowPlayingMovies()
    }

    fun getTrendMovies() = viewModelScope.launch {
        when (val result = repository.getTrendMovies()) {
            is Resource.Error -> _error.value = result.exception.message
            is Resource.Success -> _movies.value = result.data
        }
    }

}