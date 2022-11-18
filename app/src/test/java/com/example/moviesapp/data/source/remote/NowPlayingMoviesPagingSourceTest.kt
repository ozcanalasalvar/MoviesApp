package com.example.moviesapp.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.moviesapp.FakeMovieService
import com.example.moviesapp.data.model.list.MovieDto
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class NowPlayingMoviesPagingSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    lateinit var service: FakeMovieService

    lateinit var pagingSource: NowPlayingMoviesPagingSource

    companion object {
        val response: List<MovieDto> =
            listOf(TestData.provideRemoteMoviesFromAssets().results.first())
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        service = FakeMovieService()
        service.init()
        pagingSource = NowPlayingMoviesPagingSource(service)
    }


    @Test
    fun `movies paging source refresh - success`() = runTest {
        val expectedResult = PagingSource.LoadResult.Page(
            data = response,
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
    fun `movie paging source load - failure - http error`() = runBlocking {
        service.shouldThrowException(true)

        val expectedResult =
            PagingSource.LoadResult.Error<Int, MovieDto>(
                HttpException(
                    Response.error<ResponseBody>(
                        404,
                        "some content".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            )
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult.toString()).isEqualTo(result.toString())

    }
}