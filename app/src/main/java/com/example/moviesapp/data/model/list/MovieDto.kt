package com.example.moviesapp.data.model.list

import com.squareup.moshi.Json

data class MovieDto(
    @field:Json(name = "id") var id: Int,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "original_title") val original_title: String?,
    @field:Json(name = "poster_path") val poster_path: String?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "release_date") val release_date: String,
    @field:Json(name = "video") var video: Boolean,
    @field:Json(name = "original_language") val original_language: String,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "vote_count") var vote_count: Int,
    @field:Json(name = "vote_average") val vote_average: Double,
    @field:Json(name = "backdrop_path") val backdrop_path: String?,
)

