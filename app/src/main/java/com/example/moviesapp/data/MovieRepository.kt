package com.example.moviesapp.data

import androidx.paging.PagingData
import com.example.moviesapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getTrendMovies() : Flow<Resource<List<Movie>>>

}