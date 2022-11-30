package com.example.moviesapp.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.MovieDetail
import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class MovieDetailUIState {
    object Loading : MovieDetailUIState()
    data class Success(val data: MovieDetail) : MovieDetailUIState()
    data class Error(val message: String) : MovieDetailUIState()
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _uiState: MutableLiveData<MovieDetailUIState> =
        MutableLiveData(MovieDetailUIState.Loading)
    val uiState: LiveData<MovieDetailUIState> = _uiState


    fun init(movieId: Int) {
        getMovieDetail(movieId)
    }

    private fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        repository.getMovieDetail(movieId).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _uiState.value = MovieDetailUIState.Error(result.exception.message.toString())
                }
                is Resource.Success -> {
                    _uiState.value = MovieDetailUIState.Success(result.data)
                }
            }
        }
    }

}