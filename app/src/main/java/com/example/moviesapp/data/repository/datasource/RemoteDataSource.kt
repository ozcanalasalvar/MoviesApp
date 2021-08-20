package com.example.moviesapp.data.repository.datasource

import com.example.moviesapp.data.model.detail.MovieDetailModel
import com.example.moviesapp.data.remote.ApiService
import io.reactivex.Single
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : IRemoteDataSource {

    override fun getUpComingMovies(apiKey: String, page: Int, language: String) =
        apiService.getUpComingMovies(apiKey, page, language)

    override fun getNowPlayingMovies(apiKey: String, page: Int, language: String) =
        apiService.getNowPlayingMovies(apiKey, page, language)

    override fun getMovieDetail(movieId: Int, apiKey: String, language: String) =
        apiService.getMovieDetail(movieId, apiKey, language)
}