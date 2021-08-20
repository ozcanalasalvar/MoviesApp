package com.example.moviesapp.data.remote

import com.example.moviesapp.data.model.detail.MovieDetailModel
import com.example.moviesapp.data.model.list.MovieResultModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val UPCOMING_MOVIES_URL = "movie/upcoming"
        const val NOW_PLAYING_MOVIES_URL = "movie/now_playing"
        const val DETAIL_URL = "movie/"

        const val QUERY_API_KEY = "api_key"
        const val QUERY_PAGE = "page"
        const val QUERY_LANGUAGE = "language"

    }

    @GET(UPCOMING_MOVIES_URL)
    fun getUpComingMovies(
        @Query(QUERY_API_KEY) apiKey: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_LANGUAGE) language: String
    ): Single<MovieResultModel>


    @GET(NOW_PLAYING_MOVIES_URL)
    fun getNowPlayingMovies(
        @Query(QUERY_API_KEY) apiKey: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_LANGUAGE) language: String
    ): Single<MovieResultModel>

    @GET("$DETAIL_URL{id}")
    fun getMovieDetail(
        @Path("id") movieId: Int,
        @Query(QUERY_API_KEY) apiKey: String,
        @Query(QUERY_LANGUAGE) language: String
    ): Single<MovieDetailModel>

}