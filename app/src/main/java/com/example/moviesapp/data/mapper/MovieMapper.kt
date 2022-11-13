package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.Movie
import com.example.moviesapp.data.model.list.MovieDto


fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        popularity = this.popularity,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
        image = this.poster_path ?: this.backdrop_path ?: "",
        title = this.title ?: this.original_title ?: "No title found",
        overview = this.overview,
        releaseDate = this.release_date ?: "No date found",
        originalLanguage = this.original_language,
        isVideo = this.video
    )
}