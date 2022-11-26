package com.example.moviesapp.util

import androidx.annotation.VisibleForTesting
import com.example.moviesapp.data.model.cast.CastingDto
import com.example.moviesapp.data.model.cast.CastingResponse
import com.example.moviesapp.data.model.detail.Genre
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.model.list.MovieDto
import com.example.moviesapp.data.source.remote.MovieService
import com.example.moviesapp.data.model.list.MovieResponse
import java.lang.RuntimeException

object FakeMovieService : MovieService {

    private val movies: MutableList<MovieDto> = mutableListOf<MovieDto>().apply {
        for (index in 1..10) {
            add(
                MovieDto(
                    id = index,
                    title = "title$index",
                    original_title = "original title",
                    poster_path = "path",
                    overview = "film overview",
                    release_date = "2016-08-03",
                    video = false,
                    original_language = "US",
                    popularity = 1234.0,
                    vote_count = 1,
                    vote_average = 1.0,
                    backdrop_path = "path2",
                )
            )
        }
    }

    @VisibleForTesting
    fun addMovie(movieDto: MovieDto) {
        movies.add(movieDto)
    }

    private var shouldReturnError: Boolean = false

    override suspend fun getPopularMovies(page: Int): MovieResponse {
        if (shouldReturnError)
            throw RuntimeException("Test Exception")
        else {
            return MovieResponse(results = movies, page = page, totalResults = 10, totalPages = 1)
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): MovieResponse {
        if (shouldReturnError)
            throw RuntimeException("Test Exception")
        else {
            return MovieResponse(results = movies, page = page, totalResults = 10, totalPages = 1)
        }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return MovieDetailDto(
            adult = false,
            backdrop_path = "backDrop",
            budget = 28,
            genres = listOf(Genre(1, "1")),
            homepage = "homePage",
            id = 1,
            imdb_id = "tt0137523",
            original_language = "US",
            original_title = "original title",
            overview = "movie overview",
            popularity = 28.0,
            poster_path = "posterPath",
            release_date = "2016-08-03",
            revenue = 1,
            runtime = 100,
            status = "status",
            tagline = "tagline",
            title = "title",
            video = false,
            vote_average = 5.0,
            vote_count = 288,
        )
    }

    override suspend fun getMovieCast(movieId: Int): CastingResponse {
        return CastingResponse(
            id = 1, cast = listOf(
                CastingDto(
                    adult = false,
                    cast_id = 1,
                    character = "character",
                    credit_id = "28",
                    gender = 2,
                    id = 1,
                    known_for_department = "Acting",
                    name = "Edward Norton",
                    order = 0,
                    original_name = "Edward Norton",
                    popularity = 7.861,
                    profile_path = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg",
                )
            )
        )
    }

    @VisibleForTesting
    fun shouldThrowException(shouldThrow: Boolean) {
        shouldReturnError = shouldThrow
    }
}