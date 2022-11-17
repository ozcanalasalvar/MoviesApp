package com.example.moviesapp.data.util

import com.squareup.moshi.Json

data class MovieResponse<T> (
    @field:Json(name = "results") val results: T,
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int,
)