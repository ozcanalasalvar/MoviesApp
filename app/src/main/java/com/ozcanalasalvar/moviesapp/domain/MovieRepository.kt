package com.ozcanalasalvar.moviesapp.domain

import androidx.paging.PagingData
import com.ozcanalasalvar.moviesapp.domain.model.MovieDetail
import com.ozcanalasalvar.moviesapp.domain.model.Movie
import com.ozcanalasalvar.moviesapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getTrendMovies() : Resource<List<Movie>>

    suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>

}