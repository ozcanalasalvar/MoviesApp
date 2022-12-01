package com.ozcanalasalvar.moviesapp.data

import com.ozcanalasalvar.moviesapp.data.mapper.toMovie
import com.ozcanalasalvar.moviesapp.data.model.detail.Genre
import com.ozcanalasalvar.moviesapp.data.model.detail.MovieDetailDto
import com.ozcanalasalvar.moviesapp.data.source.remote.MovieService
import com.ozcanalasalvar.moviesapp.data.util.Resource
import com.ozcanalasalvar.moviesapp.data.util.isSuccess
import com.ozcanalasalvar.moviesapp.util.MainCoroutineRule
import com.ozcanalasalvar.moviesapp.util.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private lateinit var repository: MovieRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var service: MovieService

    private lateinit var  detailDto:MovieDetailDto


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val testDispatcher = UnconfinedTestDispatcher()
        repository = MovieRepositoryImpl(service, testDispatcher)


        detailDto = MovieDetailDto(
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

    @Test
    fun `getTrendMovies , returns list of Movie`() = runTest {
        Mockito.`when`(service.getPopularMovies(any()))
            .thenReturn(TestData.provideRemoteMoviesFromAssets())

        val result = repository.getTrendMovies()

        val expected = TestData.provideRemoteMoviesFromAssets().results.first().toMovie()

        assertThat(result.isSuccess).isTrue()
        assertThat((result as Resource.Success).data.first()).isEqualTo(expected)
    }

    @Test
    fun `getTrendMovies , returns throw exception`() = runTest {
        val error = RuntimeException("Test Exception", Throwable())
        Mockito.`when`(service.getPopularMovies(any())).thenThrow(error)

        val result = repository.getTrendMovies()


        assertThat(result.isSuccess).isFalse()
        assertThat((result as Resource.Error).exception).isEqualTo(error)
    }


    @Test
    fun `getMovieDetail , returns MovieDetail with casting data`() = runTest {
        Mockito.`when`(service.getMovieDetail(any())).thenReturn(detailDto)
        Mockito.`when`(service.getMovieCast(any()))
            .thenReturn(TestData.provideCastingFromAssets(any()))
        val result = repository.getMovieDetail(1).first()

        assertThat(result.isSuccess).isTrue()
        assertThat((result as Resource.Success).data.id).isEqualTo(550)
        assertThat(result.data.castings?.first()).isNotNull()

        val expected = TestData.provideCastingFromAssets(550).cast.first().id
        assertThat(result.data.castings?.first()?.id).isEqualTo(expected)
        assertThat(result.data.castings?.first()).isInstanceOf(Casting::class.java)
    }

    @Test
    fun `getMovieDetail , casting fails, returns MovieDetail casting null`() = runTest {
        Mockito.`when`(service.getMovieDetail(any())).thenReturn(detailDto)
        val error = RuntimeException("Test Exception", Throwable())
        Mockito.`when`(service.getMovieCast(any()))
            .thenThrow(error)
        val result = repository.getMovieDetail(1).first()

        assertThat(result.isSuccess).isTrue()
        assertThat((result as Resource.Success).data.id).isEqualTo(550)
        assertThat(result.data.castings?.first()).isNull()
    }

    @Test
    fun `getMovieDetail request fails, returns MovieDetail error`() = runTest {
        val error = RuntimeException("Test Exception", Throwable())
        Mockito.`when`(service.getMovieDetail(any())).thenThrow(error)


        val result = repository.getMovieDetail(1).first()

        assertThat(result.isSuccess).isFalse()
        assertThat((result as Resource.Error).exception).isEqualTo(error)
    }
}