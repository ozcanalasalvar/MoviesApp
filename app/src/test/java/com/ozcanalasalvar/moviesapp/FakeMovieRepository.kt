package com.ozcanalasalvar.moviesapp

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingData
import com.ozcanalasalvar.moviesapp.domain.model.Movie
import com.ozcanalasalvar.moviesapp.domain.model.MovieDetail
import com.ozcanalasalvar.moviesapp.domain.repository.MovieRepository
import com.ozcanalasalvar.moviesapp.data.mapper.toMovie
import com.ozcanalasalvar.moviesapp.data.mapper.toMovieDetail
import com.ozcanalasalvar.moviesapp.data.model.detail.Genre
import com.ozcanalasalvar.moviesapp.data.model.detail.MovieDetailDto
import com.ozcanalasalvar.moviesapp.data.model.list.MovieResponse
import com.ozcanalasalvar.moviesapp.domain.util.Resource
import com.ozcanalasalvar.moviesapp.util.TestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class FakeMovieRepository : MovieRepository {

    private var shouldReturnError = false

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

    @VisibleForTesting
    fun setReturnError(value: Boolean) {
        this.shouldReturnError = value
    }

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> = flow {
        return@flow
    }

    override suspend fun getTrendMovies(): Resource<List<Movie>> {
        if (shouldReturnError)
            return Resource.Error(Exception("Test Exception"))
        return Resource.Success(movies.results.map { it.toMovie() })
    }

    override suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        if (shouldReturnError){
            emit(Resource.Error(Exception("Test exception")))
            return@flow
        }

        val dto = MovieDetailDto(
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
        emit(Resource.Success(dto.toMovieDetail(TestData.provideCastingFromAssets(550).cast)))

    }
}