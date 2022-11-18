package com.example.moviesapp.util

import com.example.moviesapp.data.model.cast.CastingDto
import com.example.moviesapp.data.model.cast.CastingResponse
import com.example.moviesapp.data.model.list.MovieDto
import com.example.moviesapp.data.model.list.MovieResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

object TestData {

    private val moshi = Moshi.Builder().build()
    private val moviesResponseGenericType: Type = Types.newParameterizedType(
        MovieResponse::class.java, MovieDto::class.java
    )
    private val remoteMoviesAdapter: JsonAdapter<MovieResponse> =
        moshi.adapter(moviesResponseGenericType)


    private val castingResponseGenericType: Type = Types.newParameterizedType(
        CastingResponse::class.java, CastingDto::class.java
    )


    private val castingAdapter: JsonAdapter<CastingResponse> =
        moshi.adapter(castingResponseGenericType)

    fun provideRemoteMoviesFromAssets(): MovieResponse {
        return remoteMoviesAdapter.fromJson(
            FileReaderUtil.getJson(path = "movies.json")
        ) ?: MovieResponse(
            results = listOf(),
            page = 0,
            totalPages = 0,
            totalResults = 0,
        )
    }

    fun provideCastingFromAssets(movieId: Int): CastingResponse {
        return castingAdapter.fromJson(
            FileReaderUtil.getJson(path = "casting.json")
        )?.copy(id = movieId) ?: CastingResponse(
            id = movieId,
            cast = listOf()
        )
    }

}