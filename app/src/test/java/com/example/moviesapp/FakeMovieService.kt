package com.example.moviesapp

import androidx.annotation.VisibleForTesting
import com.example.moviesapp.data.model.cast.CastingDto
import com.example.moviesapp.data.model.cast.CastingResponse
import com.example.moviesapp.data.model.detail.Genre
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.model.list.MovieDto
import com.example.moviesapp.data.source.remote.MovieService
import com.example.moviesapp.data.model.list.MovieResponse
import com.example.moviesapp.util.TestData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeMovieService : MovieService {

    private var movies: MovieResponse = TestData.provideRemoteMoviesFromAssets()

    private val movieMap: MutableMap<Int, MovieResponse> = mutableMapOf()

    init {
        movies.results.forEachIndexed { index, movieDto ->
            movieMap[index + 1] = MovieResponse(
                results = listOf(movieDto),
                page = index + 1,
                totalPages = movies.results.size,
                totalResults = movies.results.size,
            )
        }
    }

    private var throwException: Boolean = false

    override suspend fun getPopularMovies(page: Int): MovieResponse = movies

    override suspend fun getNowPlayingMovies(page: Int): MovieResponse {
        if (throwException)
            throw HttpException(
                Response.error<ResponseBody>(
                    404,
                    "some content".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        else {
            return movieMap[page]!!
        }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return MovieDetailDto(
            adult = false,
            backdrop_path = "backDrop",
            budget = 28,
            genres = listOf(Genre(1, "1")),
            homepage = "homePage",
            id = 550,
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
        return TestData.provideCastingFromAssets(movieId)
    }

    @VisibleForTesting
    fun shouldThrowException(shouldThrow: Boolean) {
        throwException = shouldThrow
    }
}