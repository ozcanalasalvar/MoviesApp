package com.ozcanalasalvar.moviesapp.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ozcanalasalvar.moviesapp.FakeMovieRepository
import com.ozcanalasalvar.moviesapp.data.mapper.toMovie
import com.ozcanalasalvar.moviesapp.util.MainCoroutineRule
import com.ozcanalasalvar.moviesapp.util.TestData
import com.ozcanalasalvar.moviesapp.util.getOrAwaitValueTest
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

    private lateinit var repository: FakeMovieRepository

    @Before
    fun setUp() {
        repository = FakeMovieRepository()
        viewModel = MoviesViewModel(repository)
    }


    @Test
    fun `fetch trend movies ,  return success`() = runTest {
        viewModel.getTrendMovies()
        val result = viewModel.movies.getOrAwaitValueTest()

        val expected = TestData.provideRemoteMoviesFromAssets().results.first().toMovie()

        assertThat(result).isNotNull()
        assertThat(result.first()).isEqualTo(expected)
    }

    @Test
    fun `fetch movies , return error`() = runTest {
        repository.setReturnError(true)
        viewModel.getTrendMovies()
        val result = viewModel.error.getOrAwaitValueTest()


        assertThat(result).isNotNull()
        assertThat(result).isEqualTo("Test Exception")
    }
}