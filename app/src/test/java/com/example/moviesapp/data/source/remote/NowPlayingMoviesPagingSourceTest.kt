package com.example.moviesapp.data.source.remote

import androidx.paging.PagingSource
import com.example.moviesapp.data.model.list.MovieDto
import com.example.moviesapp.data.model.list.MovieResponse
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class NowPlayingMoviesPagingSourceTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var service: MovieService

    private lateinit var pagingSource: NowPlayingMoviesPagingSource

    lateinit var response: MovieResponse
    lateinit var response2: MovieResponse


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        pagingSource = NowPlayingMoviesPagingSource(service)

        response = TestData.provideRemoteMoviesFromAssets()
        response2 = response.copy(page = 2)
    }


    @Test
    fun `movies paging source refresh - success`() = runTest {
        Mockito.`when`(service.getNowPlayingMovies(any())).thenReturn(response)


        val expectedResult = PagingSource.LoadResult.Page(
            data = response.results,
            prevKey = null,
            nextKey = 2
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `movie paging source load - failure - http error`() = runTest {
        val error = RuntimeException("404", Throwable())
        Mockito.`when`(service.getNowPlayingMovies(any())).thenThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, MovieDto>(error)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertThat(expectedResult.toString()).isEqualTo(result.toString())

    }


    @Test
    fun `movie paging source refresh - success`() = runTest {
        Mockito.`when`(service.getNowPlayingMovies(any())).thenReturn(response)

        val expectedResult = PagingSource.LoadResult.Page(
            data = response.results,
            prevKey = null,
            nextKey = 2
        )


        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `movie paging source append - success`() = runTest {
        Mockito.`when`(service.getNowPlayingMovies(any())).thenReturn(response2)

        val expectedResult = PagingSource.LoadResult.Page(
            data = response.results,
            prevKey = 2,
            nextKey = 3
        )


        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 2,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `movie paging source prepend - success`() = runTest {
        Mockito.`when`(service.getNowPlayingMovies(any())).thenReturn(response)

        val expectedResult = PagingSource.LoadResult.Page(
            data = response.results,
            prevKey = null,
            nextKey = 2
        )


        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult).isEqualTo(result)
    }
}