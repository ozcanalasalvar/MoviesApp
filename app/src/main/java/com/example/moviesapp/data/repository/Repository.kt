package com.example.moviesapp.data.repository

import com.example.moviesapp.data.repository.datasource.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    private val language = "en-US"

    fun getUpComingMovies(apiKey: String, page: Int) =
        remoteDataSource.getUpComingMovies(apiKey, page, language)

    fun getNowPlayingMovies(apiKey: String, page: Int) =
        remoteDataSource.getNowPlayingMovies(apiKey, page, language)

    fun getMovieDetail(movieId: Int, apiKey: String) =
        remoteDataSource.getMovieDetail(movieId, apiKey, language)
}