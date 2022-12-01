package com.ozcanalasalvar.moviesapp.data.source.remote

import com.ozcanalasalvar.moviesapp.util.FileReaderUtil
import com.ozcanalasalvar.moviesapp.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class MovieServiceTest {
    //This will test if our data classes are well mapped with the expected response.
    private val mockWebServer = MockWebServer()
    private lateinit var movieService: MovieService

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val movieId = 550


    @Before
    fun setUp() {
        mockWebServer.start()
        mockWebServer.dispatcher = setUpMockWebServerDispatcher()
        setUpMovieRetrofitService()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Get popular movies remote response structure match JSON Server response`() = runTest {
        val movies = movieService.getPopularMovies(page = 1).results

        assertThat(movies).hasSize(20)
        assertThat(movies.first().id).isEqualTo(19404)

    }


    @Test
    fun `Get nowPlaying movies remote response structure match JSON Server response`() = runTest {
        val movies = movieService.getNowPlayingMovies(page = 1).results

        assertThat(movies).hasSize(20)
        assertThat(movies.first().id).isEqualTo(19404)

    }

    @Test
    fun `Get Movie Cast movies remote response structure match JSON Server response`() = runTest {
        val casting = movieService.getMovieCast(movieId)

        assertThat(casting.id).isEqualTo(movieId)
        assertThat(casting.cast).hasSize(2)
        assertThat(casting.cast.first().cast_id).isEqualTo(4)

    }

    private fun setUpMovieRetrofitService() {

        val client = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    var request: Request = chain.request()
                    val url: HttpUrl =
                        request.url.newBuilder()
                            .addQueryParameter(MovieService.QUERY_API_KEY, "ApiKey")
                            .addQueryParameter(MovieService.QUERY_LANGUAGE, "en-US")
                            .build()
                    request = request.newBuilder().url(url).build()
                    return chain.proceed(request)
                }

            })
            .build()

        movieService = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .build()
            .create(MovieService::class.java)
    }

    private fun setUpMockWebServerDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            println("BASE_URL${request.path}")
            return when (request.path) {
                "/movie/popular?page=1&api_key=ApiKey&language=en-US", "/movie/now_playing?page=1&api_key=ApiKey&language=en-US" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(FileReaderUtil.getJson("movies.json"))
                }
                "/movie/$movieId/credits?api_key=ApiKey&language=en-US" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(FileReaderUtil.getJson("casting.json"))
                }
                else -> MockResponse().setResponseCode(404)
            }
        }
    }
}