package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.Casting
import com.example.moviesapp.data.Movie
import com.example.moviesapp.data.MovieDetail
import com.example.moviesapp.data.model.cast.CastingDto
import com.example.moviesapp.data.model.detail.Genre
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.model.list.MovieDto
import com.example.moviesapp.util.reformatDate


fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        popularity = this.popularity,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
        image = (this.poster_path ?: this.backdrop_path)?.toImageUrl(),
        title = this.title ?: this.original_title ?: "No title found",
        overview = this.overview ?: "No overview found",
        releaseDate = this.release_date.reformatDate(),
        originalLanguage = this.original_language,
        isVideo = this.video
    )
}

fun MovieDetailDto.toMovieDetail(list: List<CastingDto>?): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title ?: this.original_title ?: "No title found",
        image = (this.poster_path ?: this.backdrop_path)?.toImageUrl(),
        voteAverage = this.vote_average,
        overview = this.overview,
        voteCount = this.vote_count,
        popularity = this.popularity,
        duration = this.runtime.durationToString(),
        releaseDate = this.release_date.reformatDate(),
        isVideo = this.video,
        castings = list?.map {
            it.toCasting()
        },
        genres = this.genres.toUIText()
    )
}

fun CastingDto.toCasting(): Casting {
    return Casting(
        id = this.id,
        name = this.name ?: this.original_name ?: "No name found",
        profileImage = this.profile_path?.toImageUrl()
    )
}

fun List<Genre>.toUIText(): String {
    var text = ""
    this.forEachIndexed { index, genre ->
        text += genre.name

        if (index != this.size - 1)
            text += " / "
    }
    return text
}


private fun String.toImageUrl(): String {
    return "https://image.tmdb.org/t/p/w500$this"
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