package com.example.moviesapp.data

import com.example.moviesapp.FakeMovieService
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.data.util.Resource
import com.example.moviesapp.data.util.isSuccess
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private lateinit var repository: MovieRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        val movieService = FakeMovieService()
        val testDispatcher = UnconfinedTestDispatcher()
        repository = MovieRepositoryImpl(movieService,testDispatcher)
    }

    @Test
    fun `getTrendMovies , returns list of Movie`() = runTest {

        val result = repository.getTrendMovies()

        val expected = TestData.provideRemoteMoviesFromAssets().results.first().toMovie()

        assertThat(result.isSuccess).isTrue()
        assertThat((result as Resource.Success).data.first()).isEqualTo(expected)
    }


    @Test
    fun `getMovieDetail , returns MovieDetail with casting data`() = runTest {
        val result = repository.getMovieDetail(550).first()

        assertThat(result.isSuccess).isTrue()
        assertThat((result as Resource.Success).data.id).isEqualTo(550)
        assertThat(result.data.castings?.first()).isNotNull()

        val expected = TestData.provideCastingFromAssets(550).cast.first().id
        assertThat(result.data.castings?.first()?.id).isEqualTo(expected)
        assertThat(result.data.castings?.first()).isInstanceOf(Casting::class.java)
    }
}