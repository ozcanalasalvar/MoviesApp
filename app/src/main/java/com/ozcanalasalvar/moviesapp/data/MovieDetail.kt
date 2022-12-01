package com.ozcanalasalvar.moviesapp.data

data class MovieDetail(
    val id: Int,
    val title: String,
    val image: String?,
    val voteAverage: Double,
    val overview: String,
    val voteCount: Int,
    val popularity: Double,
    val duration: String,//Format
    val releaseDate: String,
    val isVideo: Boolean,
    val castings: List<Casting>?,
    val genres: String
)

data class Casting(
    val id: Int,
    val name: String,
    val profileImage: String?
)