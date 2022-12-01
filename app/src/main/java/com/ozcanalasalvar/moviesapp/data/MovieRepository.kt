package com.ozcanalasalvar.moviesapp.data

import androidx.paging.PagingData
import com.ozcanalasalvar.moviesapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getTrendMovies() : Resource<List<Movie>>

    suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>

}