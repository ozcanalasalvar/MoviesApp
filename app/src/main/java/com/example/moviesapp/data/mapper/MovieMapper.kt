package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.Casting
import com.example.moviesapp.data.Movie
import com.example.moviesapp.data.MovieDetail
import com.example.moviesapp.data.model.cast.CastingDto
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.model.list.MovieDto


fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        popularity = this.popularity,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
        image = this.poster_path.toMovieImage() ?: this.backdrop_path.toMovieImage() ?: "",
        title = this.title ?: this.original_title ?: "No title found",
        overview = this.overview,
        releaseDate = this.release_date ?: "No date found",
        originalLanguage = this.original_language,
        isVideo = this.video
    )
}

fun MovieDetailDto.toMovieDetail(list: List<CastingDto>?): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title ?: this.original_title ?: "",
        image = this.poster_path.toMovieImage() ?: this.backdrop_path.toMovieImage(),
        voteAverage = this.vote_average,
        overview = this.overview,
        voteCount = this.vote_count,
        popularity = this.popularity,
        duration = this.runtime.durationToString(),
        releaseDate = this.release_date,
        isVideo = this.video,
        castings = list?.map {
            it.toCasting()
        }
    )
}

fun CastingDto.toCasting(): Casting {
    return Casting(
        id = this.id,
        name = this.name ?: this.original_name ?: "",
        profileImage = this.profile_path.toMovieImage()
    )
}


private fun String?.toMovieImage(): String? {
    return if (this.isNullOrEmpty()) null else "https://www.themoviedb.org/t/p/w440_and_h660_face$this"
}

private fun Int.durationToString(): String {
    val hour = this / 60
    val minute = this % 60
    var appendText = ""
    if (hour > 0)
        appendText += "$hour hr"
    if (minute > 0)
        appendText += "$minute mins"
    return appendText
}