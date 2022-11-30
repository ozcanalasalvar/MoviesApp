package com.example.moviesapp.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.FakeMovieRepository
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

class MovieDetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var repository: FakeMovieRepository

    @Before
    fun setUp() {
        repository = FakeMovieRepository()
        viewModel = MovieDetailViewModel(repository)
    }


    @Test
    fun `ui state initial ,  return loading`() = runTest {
        val result = viewModel.uiState.getOrAwaitValueTest()
        assertThat(result).isEqualTo(MovieDetailUIState.Loading)

    }

    @Test
    fun `init viewModel ,  return ui state success`() = runTest {

        viewModel.init(550)
        val result = viewModel.uiState.getOrAwaitValueTest()
        assertThat(result is MovieDetailUIState.Success).isTrue()
        assertThat((result as MovieDetailUIState.Success).data.id).isEqualTo(550)

        val expected = TestData.provideCastingFromAssets(550).cast
        assertThat(result.data.castings?.size).isEqualTo(expected.size)
    }

    @Test
    fun `init viewModel ,  return ui state error`() = runTest {

        repository.setReturnError(true)
        viewModel.init(550)
        val result = viewModel.uiState.getOrAwaitValueTest()
        assertThat(result is MovieDetailUIState.Error).isTrue()
        assertThat((result as MovieDetailUIState.Error).message).isEqualTo("Test exception")
    }
}