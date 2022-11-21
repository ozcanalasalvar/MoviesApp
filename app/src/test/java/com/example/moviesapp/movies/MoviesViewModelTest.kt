package com.example.moviesapp.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.FakeMovieService
import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.MovieRepositoryImpl
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.data.util.Resource
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.TestData
import com.example.moviesapp.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesViewModel

    private lateinit var repository: MovieRepository

    private lateinit var service: FakeMovieService

    @Before
    fun setUp() {
        service = FakeMovieService()
        repository = MovieRepositoryImpl(service)
        viewModel = MoviesViewModel(repository)
    }


    @Test
    fun `fetch trend movies ,  return success`() = runTest {
        viewModel.getTrendMovies()
        val result = viewModel.movies.getOrAwaitValueTest()

        val expected = TestData.provideRemoteMoviesFromAssets().results.first().toMovie()

        assertThat(result).isNotNull()
        assertThat(result!!.first()).isEqualTo(expected)
    }

    @Test
    fun `fetch movies , return error`() = runTest {
        service.setReturnError(true)
        viewModel.getTrendMovies()
        val result = viewModel.error.getOrAwaitValueTest()


        assertThat(result).isNotNull()
        assertThat(result).isEqualTo("Test exception")
    }
}