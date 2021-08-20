package com.example.moviesapp.data.repository.datasource

import com.example.moviesapp.data.model.detail.MovieDetailModel
import com.example.moviesapp.data.model.list.MovieResultModel
import io.reactivex.Observable
import io.reactivex.Single

interface IRemoteDataSource {

    fun getUpComingMovies(apiKey: String, page: Int,language: String): Single<MovieResultModel>

    fun getNowPlayingMovies(apiKey: String, page: Int,language: String): Single<MovieResultModel>

    fun getMovieDetail(
        movieId: Int,
        apiKey: String,
        language: String
    ): Single<MovieDetailModel>
}
