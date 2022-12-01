package com.ozcanalasalvar.moviesapp.data.model.list

import com.squareup.moshi.Json

data class MovieResponse (
    @field:Json(name = "results") val results: List<MovieDto>,
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int,
)